package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.bit2015.network.chat.ChatServer;

public class HttpThread extends Thread {
	private static final String WEB_ROOT = "C:\\bit2015\\workspace\\network\\web-root";

	private Socket socket;

	public HttpThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;

		try {
			// 1. 스트림 얻기
			bufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			outputStream = socket.getOutputStream();

			// 2. Remote Host Information
			InetSocketAddress inetSocketAddress = (InetSocketAddress) socket
					.getRemoteSocketAddress();
			ChatServer.log("연결됨 from " + inetSocketAddress.getHostName() + ":"
					+ inetSocketAddress.getPort());

			// 3. 요청처리
			String request = bufferedReader.readLine();
			if (request != null) {
				HttpServer.log("request:" + request);

				String[] tokens = request.split(" ");
				if ("GET".equals(tokens[0]) == true) {
					sendStaticResource(outputStream, tokens[1], tokens[2]);
				} else {
					ChatServer.log("error: 지원하지 않는 요청 명령(" + tokens[0] + ")");
				}
			}

			// 4. 연결 끊기
			bufferedReader.close();
			outputStream.close();
			socket.close();

		} catch (IOException ex) {
			HttpServer.log("error:" + ex);
		}
	}

	private void sendStaticResource(OutputStream outputStream, String path,
			String protocol) throws IOException { //응답할때 써야하는 스트림 , 패스, 도큐먼트 경로

		if ("/".equals(path)) {
			path = "/index.html";
		}

		String extension = path.substring(path.lastIndexOf("."));

		if (".html".equals(extension) == false
				&& ".htm".equals(extension) == false) {
			sendError404(outputStream, protocol); //404 페이지읎다   200은 성공
			return;
		}

		File file = new File(WEB_ROOT, path);
		if (file.exists() == false) {
			sendError404(outputStream, protocol);
			return;
		}
		
		// 요청한 파일을 읽어서 브라우저에게 전송한다.
		// ..
		
		//header	
	    outputStream.write( ( protocol + " 200 OK\r\n" ).getBytes() );
	    outputStream.write( "Content-Type:text/html; charset=UTF-8\r\n".getBytes() );

	    outputStream.write( "\r\n".getBytes() );

	    // body
	    FileInputStream fis = new FileInputStream( file );
	    byte[] buffer = new byte[ 1024 ];
			 
	    int countReadByte = fis.read( buffer, 0, 1024 );
	    while( countReadByte >= 0 ) {
	        outputStream.write( buffer, 0, countReadByte );
	        countReadByte = fis.read( buffer, 0, 1024 );
	    }

	    outputStream.flush();
	    fis.close();
	}

	private void sendError404(OutputStream outputStream, String protocol) throws IOException {
		// header
		outputStream.write((protocol + " 404 File Not Found\r\n").getBytes());
		outputStream.write("Content-Type:text/html; charset=UTF-8\r\n"
				.getBytes());

		outputStream.write("\r\n".getBytes());

		// body
		outputStream.write("<h1>File Not Found</h1>".getBytes());
		outputStream.flush();
	}
}

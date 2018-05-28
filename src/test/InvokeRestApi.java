package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class InvokeRestApi {

	public static void main(String[] args) {
		InvokeRestApi obj = new InvokeRestApi();
		obj.m();
	}

	public void m() {
		try {
			String urlString = "http://thecatapi.com/api/images/getvotes?api_key=[YOUR-API-KEY]&sub_id=12345";
			/*
			 * XML Response :
			 * 
			 */
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "Application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Accept-Language", "en");

			conn.connect();

			System.out.println("API Response recevied : " + conn.getResponseCode());

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : http error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			// unmarshal requires StringReader so convert
			StringReader stringReaderXML = new StringReader(sb.toString());

			System.out.println("API Response Recevied XML : \n" + sb.toString() + "\n");
			
			/* Received Response XML : 
 <response>
 <data/>
			<apierror>
				<message>Sorry, looks like your API key is invalid. Bummer.</message>
			</apierror>
</response>
			 */

			// Converting XML into Object
			JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Response response = (Response) unmarshaller.unmarshal(stringReaderXML);
			// Now all values are mapped from received api response xml to POJO
			// Read Message node value
			System.out.println(response.getApierror().getMessage());

			conn.disconnect();

		} catch (MalformedURLException e) {

		} catch (IOException e) {
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

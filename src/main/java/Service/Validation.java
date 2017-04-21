package Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validation {

	
	public boolean isValidStock(String stock) {
		if(stock.length()==0)return false;
		try
		{
		  Integer.parseInt(stock);
		}
		catch(NumberFormatException e)
		{
		  return false;
		}
		return true;
	}

	public boolean isValidDateOfRelease(String dateOfRelease) {
		if(dateOfRelease.length()==0)return false;
		Date date = null;
		try {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		    date = sdf.parse(dateOfRelease);
		    if (!dateOfRelease.equals(sdf.format(date))) {
		        date = null;
		    }
		} catch (ParseException ex) {
		    ex.printStackTrace();
		    return false;
		}
		if (date == null) {
		    return false;
		} else {
		    return true;
		}
	}

	public boolean isValidPrice(String price) {
		if(price.length()==0)return false;
		try
		{
		  Double.parseDouble(price);
		}
		catch(NumberFormatException e)
		{
		  return false;
		}
		return true;
	}

	public boolean isValidWriter(String writer) {
		int x = 0;
		for(int i = 0;i<writer.length();i++){
			char character = writer.charAt(i);
			int asciiCode = (int) character;
			if((asciiCode>=65 && asciiCode<=90) || (asciiCode>=97 && asciiCode<=122)
					|| asciiCode == 32 || asciiCode == 46){
				x++;
			}
		}
		System.out.println(x);
		if(x == writer.length()){
			return true;
		}else{
			return false;
		}
	}
}

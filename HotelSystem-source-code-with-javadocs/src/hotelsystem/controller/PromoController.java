package hotelsystem.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.Promo;


public class PromoController extends DatabaseController{
	private static final String DB_PATH = "DB/Promo.dat";
	private static PromoController instance = null;
	private final ArrayList<Promo> promoList = new ArrayList<>();
	
	
	private PromoController() {}

	
	public static PromoController getInstance() {
		if (instance == null) {
			instance = new PromoController();
		}
		return instance;
	}

	
	public Promo getPromo(int promoID) {
		for (Promo promo : promoList) {
			if (promo.getPromo_ID()==promoID){
				return promo;
			}
		}
		return null;
	}
	
	
	public Promo findPromo(int roomType_ID, Date start, Date end) {
		for (Promo promo : promoList) {
			if (promo.getRoomType_ID()==roomType_ID){
				if((start.before(promo.getPromo_from())&&end.before(promo.getPromo_from()))){
					return null;
				}
				else if((start.after(promo.getPromo_to())&&end.after(promo.getPromo_to()))){
					return null;
				}
				else {
					return promo;
				}
			}
		}
		return null;
	}

	
	public void removePromo(Promo promo) {
		promoList.remove(promo);
		SaveDB();
	}

	
	public void addPromo(Promo promo) {
		promoList.add(promo);
		SaveDB();
	}
	
	
	public ArrayList<Promo> getPromoList() {
    	ArrayList<Promo> getPromo = new ArrayList<>();
    	for(Promo promo : promoList){
            	getPromo.add(promo);
        }
    	return getPromo;
    }
	
	
	@Override
	public boolean LoadDB() {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		promoList.clear();
		if (checkFileExist(DB_PATH)) {
			try {
				// read String from text file
				ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

				for (String st : stringArray) {
					// get individual 'fields' of the string separated by SEPARATOR
					StringTokenizer token = new StringTokenizer(st, SEPARATOR);  //pass in the string to the string tokenizer using delimiter ","
					int id = Integer.parseInt(token.nextToken().trim());
					int roomTypeID = Integer.parseInt(token.nextToken().trim());
					String promo_desc = token.nextToken().trim();  	
					double discount_amt = Double.parseDouble(token.nextToken().trim());                    
					Date dateFrom = null;
                    Date dateTo = null;
					try {
						dateFrom = df.parse(token.nextToken().trim());
						dateTo = df.parse(token.nextToken().trim());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Promo promo = new Promo(id,roomTypeID,promo_desc,discount_amt,dateFrom,dateTo);
					promoList.add(promo);
				}

				System.out.printf("PromoController: %,d Entries Loaded.\n", promoList.size());
				return true;

			} catch (IOException | NumberFormatException ex) {
				System.out.println("[ERROR] Read Error! Database for Promo is not loaded!");
				//Logger.getLogger(PromoController.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}

		} else {
			System.out.println("[ERROR] File not found! Database for Promo is not loaded!");
			return false;
		}
	}

	
	@Override
	public void SaveDB() {
		List<String> output = new ArrayList<>();
		StringBuilder st = new StringBuilder();
		if (checkFileExist(DB_PATH)) {
			// Parse Content to Write
			for (Promo promo : promoList) {
				st.setLength(0); 					// Clear Buffer
				st.append(promo.getPromo_ID()); 	// ID
                st.append(SEPARATOR);
                st.append(promo.getRoomType_ID());
                st.append(SEPARATOR);
                st.append(promo.getPromo_desc());
                st.append(SEPARATOR);
                st.append(promo.getDiscount_amt());
                st.append(SEPARATOR);
                st.append(promo.getPromo_from());
                st.append(SEPARATOR);
                st.append(promo.getPromo_to());
                st.append(SEPARATOR);

				output.add(st.toString());
			}

			// Attempt to save to file
			try {
				write(DB_PATH, output);
				//System.out.printf("PromoController: %,d Entries Saved.\n",
						//output.size());
			} catch (Exception ex) {
				System.out.println("[Error] Write Error! Changes not saved!");
			}
		} else {
			System.out.println("[ERROR] File not found! Changes not Saved!");
		}
	}


}

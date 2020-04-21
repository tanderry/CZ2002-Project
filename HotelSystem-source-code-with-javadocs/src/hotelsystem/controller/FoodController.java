package hotelsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import hotelsystem.entity.Food;


public class FoodController extends DatabaseController{
	private static final String DB_PATH = "DB/Food.dat";
	private static FoodController instance = null;
	private final ArrayList<Food> foodList;

	
	private FoodController() {
		foodList = new ArrayList<>();
	}

	
	public static FoodController getInstance() {
		if (instance == null) {
			instance = new FoodController();
		}
		return instance;
	}
	
	
	public void updateFood(String foodName, Double foodPrice, String foodDescription) {
		Food food = getFood(foodName);
		food.setfood_price(foodPrice);
		food.setfood_description(foodDescription);
		SaveDB();
	}

	
	public Food getFood(String name) {
		String checkName = name.toUpperCase();
		for (Food food : foodList) {
			if (food.getfood_name().toUpperCase().equals(checkName)){
				return food;
			}
		}
		return null;
	}

	
	public void removeFood(Food food) {
		foodList.remove(food);
		SaveDB();
	}

	
	public void addFood(Food food) {
		foodList.add(food);
		SaveDB();
	}
	
	
	public ArrayList<Food> getAllFoodList() {
		ArrayList<Food> fList = new ArrayList<>();
		for(Food r : foodList) {
			fList.add(r);
		}
		return fList;
	}
	
	
	public Food getFood(int foodID) {
		for (Food food : foodList) {
			if (food.getFood_ID() == foodID){
				return food;
			}
		}
		return null;
	}

	
	@Override
	public boolean LoadDB() {
		foodList.clear();
		if (checkFileExist(DB_PATH)) {
			try {
				// read String from text file
				ArrayList<String> stringArray = (ArrayList<String>) read(DB_PATH);

				for (String st : stringArray) {
					// get individual 'fields' of the string separated by SEPARATOR
					StringTokenizer token = new StringTokenizer(st, SEPARATOR);  //pass in the string to the string tokenizer using delimiter ","
					@SuppressWarnings("unused")
					int id = Integer.parseInt(token.nextToken().trim());
					String food_name = token.nextToken().trim();  				//ID
					double food_price = Double.parseDouble(token.nextToken().trim());                    
					String food_description = token.nextToken().trim();

					Food food = new Food(food_name, food_price, food_description);
					foodList.add(food);
				}

				System.out.printf("FoodController: %,d Entries Loaded.\n", foodList.size());
				return true;

			} catch (IOException | NumberFormatException ex) {
				System.out.println("[ERROR] Read Error! Database for Food is not loaded!");
				//Logger.getLogger(PromoController.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}

		} else {
			System.out.println("[ERROR] File not found! Database for Food is not loaded!");
			return false;
		}
	}

	
	@Override
	public void SaveDB() {
		List<String> output = new ArrayList<>();
		StringBuilder st = new StringBuilder();
		if (checkFileExist(DB_PATH)) {
			// Parse Content to Write
			for (Food food : foodList) {
				st.setLength(0); 					// Clear Buffer
				st.append(food.getFood_ID()); 	// ID
                st.append(SEPARATOR);
				st.append(food.getfood_name()); 		
				st.append(SEPARATOR);
				st.append(food.getfood_price()); 		
				st.append(SEPARATOR);
				st.append(food.getfood_description()); 		
				st.append(SEPARATOR);

				output.add(st.toString());
			}

			// Attempt to save to file
			try {
				write(DB_PATH, output);
				//System.out.printf("FoodController: %,d Entries Saved.\n",
						//output.size());
			} catch (Exception ex) {
				System.out.println("[Error] Write Error! Changes not saved!");
			}
		} else {
			System.out.println("[ERROR] File not found! Changes not Saved!");
		}
	}

}

package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import drink.Drink;
import drink.Ingredient;

public class DrinkIngredientPersister {
	public static void CreateTable() {
		String sql = "CREATE TABLE IF NOT EXISTS \"drink_ingredient\" (\n" + 
				"	\"drink_name\"	TEXT NOT NULL,\n" + 
				"	\"ingredient_name\" TEXT NOT NULL\n" +
				")";
		
		try (Connection conn = DbSqlite.getConnection()){
			System.out.println("Creating drink table in database...\n");
			Statement stmt = conn.createStatement();
			
			stmt.execute(sql);
	        stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public static void DropTable() {
		String sql = "DROP TABLE IF EXISTS drink_ingredient";
		
		try (Connection conn = DbSqlite.getConnection()){
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
	        stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static void  insert(Drink d, Ingredient i) {
		String sql = "INSERT INTO main.drink_ingredient(drink_name, ingredient_name) VALUES(?, ?)";
		
		try (Connection conn = DbSqlite.getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, d.getName());
			pstmt.setString(2, i.getName());	
			pstmt.execute();
	        pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static ArrayList<Ingredient> getIngredientFromDrinkName(String drink_name) {
		ArrayList<Ingredient> res = new ArrayList<Ingredient>();
		String sql = "SELECT * FROM main.drink_ingredient WHERE drink_name = ?";
		
		try (Connection conn = DbSqlite.getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, drink_name);
			
			ResultSet rs    = pstmt.executeQuery();
			
			while(rs.next()) {
				Ingredient i = new Ingredient(rs.getString("ingredient_name"));
				res.add(i);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
		
		return res;
	}
	
	public static void removeDrinkRelation(Drink d) {
		String sql = "DELETE FROM main.drink_ingredient WHERE drink_name = ?";
		
		try (Connection conn = DbSqlite.getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, d.getName());
			pstmt.execute();
	        pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}
	
	public static void removeIngredientRelation(Ingredient i) {
		String sql = "DELETE FROM main.drink_ingredient WHERE ingredient_name = ?";
		
		try (Connection conn = DbSqlite.getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, i.getName());
			pstmt.execute();
	        pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}
	
}


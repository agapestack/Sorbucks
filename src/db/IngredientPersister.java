package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import drink.Ingredient;

public class IngredientPersister {
	
	private IngredientPersister() {}
	
	public static void CreateTable(){
		String sql = "CREATE TABLE IF NOT EXISTS ingredient (\n" + 
				"	\"id\"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" + 
				"	\"name\"	TEXT NOT NULL UNIQUE\n" + 
				")";
		
		try (Connection conn = DbSqlite.getConnection()){
			System.out.println("Creating ingredient table in database...\n");
			Statement stmt = conn.createStatement();
			
			stmt.execute(sql);
	        stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void DropTable() {
		String sql = "DROP TABLE IF EXISTS ingredient";
		
		try (Connection conn = DbSqlite.getConnection()){
			System.out.println("Drop ingredient table...\n");
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
	        stmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static void insert(Ingredient i) {
		String sql = "INSERT INTO main.ingredient(name) VALUES(?)";
		
		try (Connection conn = DbSqlite.getConnection()) {
			System.out.println("Inserting " + i.toString() + " to ingredient table");
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  i.getName());
			pstmt.execute();
		} catch(SQLException e) {
//			error code https://www.sqlite.org/c3ref/c_abort.html
//			System.out.println("Error Code: " + e.getErrorCode());
			if(e.getErrorCode() == 19) {
//				constraint error, skipping it
				
			}else {
				System.out.println(e.getMessage());	
			}
		}
	}
	
	public static void remove(Ingredient i) {
		String sql = "DELETE FROM ingredient WHERE name = ?";
		
//		Normally we would use the primary key to delete but name are unique 
		try (Connection conn = DbSqlite.getConnection()) {
			System.out.println("Removing " + i.toString() + " to ingredient table");
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  i.getName());
			pstmt.executeUpdate();
//			removing ingredient relation in drink_ingredient table
			DrinkIngredientPersister.removeIngredientRelation(i);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static ArrayList<Ingredient> getAll() {
		ArrayList<Ingredient> res = new ArrayList<Ingredient>();
		
		String sql = "SELECT * FROM ingredient";
		
		try (Connection conn = DbSqlite.getConnection()) {
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql);
			while (rs.next()) {
//	               System.out.println(
//	            		   rs.getInt("id") +  "\t" + 
//	                       rs.getString("name")
//	               );
	               Ingredient i = new Ingredient(rs.getString("name"));
	               res.add(i);
	            }
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return res;
	}
	
	public static Ingredient getIngredientByName(String name) {
		String sql = "SELECT * FROM ingredient WHERE name = ?";
		Ingredient res = null;
		
		try (Connection conn = DbSqlite.getConnection()){
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs    = pstmt.executeQuery();
			
			while(rs.next()) {
				res = new Ingredient(rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return res;
	}
	
	public static int getMaxId() {
		int res = 0;
		String sql = "SELECT * FROM ingredient ORDER BY id DESC LIMIT 1";
		try (Connection conn = DbSqlite.getConnection()) {
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery(sql);
			while(rs.next()) {
				res = rs.getInt("id");
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return res;
	}

}

package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Citizen;
import Model.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class SignUpDAO {
	
	
	
	public static ArrayList<Citizen> dbArrayList=new ArrayList<>();
	
	public static int insertAdminCostData(Citizen citizen) {
		StringBuffer insertSignUp=new StringBuffer();
		insertSignUp.append("insert into citizen ");
		insertSignUp.append("(dongho,address,address2,password,password2,name,gender,birth,email,phone) ");
		insertSignUp.append("values ");
		insertSignUp.append("(?,?,?,?,?,?,?,?,?,?) ");

		
		Connection con= null;
		PreparedStatement psmt= null;
		int count=0;

		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(insertSignUp.toString());
			
			psmt.setString(1, SignUpController.dongho);
			psmt.setString(2, citizen.getAddress());
			psmt.setString(3, citizen.getAddress2());
			psmt.setString(4, citizen.getPassword());
			psmt.setString(5, citizen.getPassword2());
			psmt.setString(6, citizen.getName());
			psmt.setString(7, citizen.getGender());
			psmt.setString(8, citizen.getBirth());
			psmt.setString(9, citizen.getEmail());
			psmt.setString(10, citizen.getPhone());

			count=psmt.executeUpdate();
			if(count == 0) {
				callAlert("���� �߻� : �ٽôٽ�");
				return count;
			}
		
		} catch (SQLException e) {
			callAlert("��ĭ �߻� : ȸ�������� �ٽ� �����ϼ���");
	
		} finally {
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
			}	
		}
		return count;
		
	}
	
	public static ArrayList<Citizen> getCitizenDataTotalData(){
		
		String selectCitizen= "select * from citizen ";
		
		Connection con= null;
		PreparedStatement psmt= null;
		ResultSet rs = null;
		try {
			con=DBUtility.getConnection();
			psmt = con.prepareStatement(selectCitizen);
			
	
			rs=psmt.executeQuery();
			if(rs == null) {
				callAlert("select ���� :  select ���������� ���˹ٶ�.");
				return null;
			}
			dbArrayList.clear();
			while(rs.next()) {
				Citizen citizen=new Citizen(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10));
				dbArrayList.add(citizen);
			}
			
			
		} catch (SQLException e) {
			callAlert("���� ���� : ����Ÿ���̽� ���Խ����߾��.. ���˹ٶ�.");
		} finally {
			 try {
				if(psmt != null) {psmt.close();}
				if(con != null) { con.close(); }
			} catch (SQLException e) {
				callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
			}	
		}		
		return dbArrayList;
		
	}

	public static int updateCitizenData(Citizen citizen) {
		StringBuffer updatecitizen =new StringBuffer();
		updatecitizen.append("update citizen set ");
		updatecitizen.append("address=?,address2=?,password=?,name=?,gender=?,birth=?,email=?,phone=? where dongho=? ");

			Connection con= null;
			PreparedStatement psmt= null;
			int count=0;
			try {
				String Dongho=citizen.getAddress()+citizen.getAddress2();
				con=DBUtility.getConnection();
				psmt = con.prepareStatement(updatecitizen.toString());
				
				psmt.setString(1, citizen.getAddress());
				psmt.setString(2, citizen.getAddress2());
				psmt.setString(3, citizen.getPassword());
				psmt.setString(4, citizen.getName());
				psmt.setString(5, citizen.getGender());
				psmt.setString(6, citizen.getBirth());
				psmt.setString(7, citizen.getEmail());
				psmt.setString(8, citizen.getPhone());
				
				psmt.setString(9,Dongho);
				

				count=psmt.executeUpdate();
				if(count == 0) {
					
					callAlert("�����ּ� ���� �Ұ� : �ּҴ� �����Ͻ� �� �����ϴ�.");
				
					return count;
				}
				
			} catch (SQLException e) {
				callAlert("���� ���� : ����Ÿ���̽� ���������߾��.. ���˹ٶ�.");
				e.printStackTrace();
			} finally {
				 try {
					if(psmt != null) {psmt.close();}
					if(con != null) { con.close(); }
				} catch (SQLException e) {
					callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
				}	
			}
	return count;
}

	
	public static int deleteCitizenData(String dongho) {
		String deletecitizen = "delete from citizen where dongho = ? ";

		Connection con = null;
		PreparedStatement psmt = null;
		int count=0;
		
		
		try {
			con = DBUtility.getConnection();
			psmt = con.prepareStatement(deletecitizen);
			psmt.setString(1, dongho);

	System.out.println(dongho);
			count= psmt.executeUpdate(); 
			if (count==0) {
				callAlert("delete ���� : delete������ ����. ���˹ٶ�");
				return count;
			}

		} catch (SQLException e) {
			callAlert("delete ���� : �����ͺ��̽� delete �����߽��ϴ�. ���˹ٶ�");
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				callAlert("�ڿ��ݱ� ���� : psmt,con �ݱ����");
			}
		}	
	
		return count;
	}
public static void callAlert(String contentText) {
	Alert alert=new Alert(AlertType.INFORMATION);
	alert.setTitle("���!");
	alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
	alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
	
	alert.showAndWait();
}
}



package Controller;


import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import Model.AdminCost;
import Model.Citizen;
import Model.Employee;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CitiLoginController implements Initializable {

	@FXML	private TextField cititextId1;
	@FXML	private TextField cititextId2;
	@FXML	private PasswordField cititextPassword;
	@FXML	private Button citibtnLogin;
	@FXML	private Button citibtnClose;
	@FXML	private Button citiBtnjoin;

	// ArrayList<AdminCost> dbArrayList=new ArrayList<>();
	ObservableList<AdminCost> t2Listdata = FXCollections.observableArrayList();

	public Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// �α��� ��ưŬ���� ���� ȭ������ �̵�
		citibtnLogin.setOnAction((e) -> {handlerBtnLoginAction();});
		// �ݱ��ư Ŭ���� ����
		citibtnClose.setOnAction((e) -> {	handlerBtnCAction();});
		// ȸ������â ���
		citiBtnjoin.setOnAction((e) -> {handlerBtnjoinAction();});

		cititextPassword.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				handlerBtnLoginAction();
			}
		});

	}

	// �α��� ��ưŬ���� ���� ȭ������ �̵�
	private void handlerBtnLoginAction() {
		if (cititextId1.getText().equals("admin") && cititextPassword.getText().equals("1")) {

			cititextId1.clear();
			cititextPassword.clear();

			try {
				// �α��� ����
				Stage adminliststage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/adlist.fxml"));
				Parent root = loader.load();

				AdminListController adminListController = loader.getController();
				adminListController.adminliststage = adminliststage;

				Scene scene = new Scene(root);
				adminliststage.setScene(scene);
				primaryStage.close();
				adminliststage.show();
				callAlert("������ �α��� ���� : �ݰ����ϴ�. \n ��ſ��Ϸ� �ǽʽÿ�");
			} catch (Exception e) {
			}

		} else {

			String id = cititextId1.getText().trim();
			String id2 = cititextId2.getText().trim();

			String pw = cititextPassword.getText().trim();
			String sql = "select address, address2, password from citizen where address=? and address2=? and password=? ";
			Parent root = null;
			try {
				Connection con = DBUtility.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);

				pstmt.setString(1, id);
				pstmt.setString(2, id2);
				pstmt.setString(3, pw);

				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					id = rs.getString("address");
					id2 = rs.getString("address2");
					pw = rs.getString("password");

					Stage citizenstage = new Stage(StageStyle.UTILITY);
					citizenstage.initModality(Modality.WINDOW_MODAL);
					citizenstage.initOwner(primaryStage);
					citizenstage.setTitle("������ ");
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/citiho.fxml"));
					root = loader.load();

					ImageView imageQue=(ImageView) root.lookup("#imageQue");
					TableView<AdminCost> tableviewCost = (TableView) root.lookup("#tableviewCost");
					Button elecChart = (Button) root.lookup("#elecChart");
					Button btnOk = (Button) root.lookup("#btnOk");
					Button waterChart = (Button) root.lookup("#waterChart");
					Button gasChart = (Button) root.lookup("#gasChart");
					Button heatChart = (Button) root.lookup("#heatChart");
					TextField textDong = (TextField) root.lookup("#textDong");
					TextField textHo = (TextField) root.lookup("#textHo");
					
					TableColumn tcelecCost = tableviewCost.getColumns().get(0);
					tcelecCost.setCellValueFactory(new PropertyValueFactory<>("electric"));
					tcelecCost.setStyle("-fx-alignment: CENTER");
					TableColumn tcwaterCost = tableviewCost.getColumns().get(1);
					tcwaterCost.setCellValueFactory(new PropertyValueFactory<>("water"));
					tcwaterCost.setStyle("-fx-alignment: CENTER");
					TableColumn tcgasCost = tableviewCost.getColumns().get(2);
					tcgasCost.setCellValueFactory(new PropertyValueFactory<>("gas"));
					tcgasCost.setStyle("-fx-alignment: CENTER");
					TableColumn tcheatCost = tableviewCost.getColumns().get(3);
					tcheatCost.setCellValueFactory(new PropertyValueFactory<>("heat"));
					tcheatCost.setStyle("-fx-alignment: CENTER");
					TableColumn tctoCost = tableviewCost.getColumns().get(4);
					tctoCost.setCellValueFactory(new PropertyValueFactory<>("joint"));
					tctoCost.setStyle("-fx-alignment: CENTER");
					TableColumn tctotalCost = tableviewCost.getColumns().get(5);
					tctotalCost.setCellValueFactory(new PropertyValueFactory<>("tatalCost"));
					tctotalCost.setStyle("-fx-alignment: CENTER");
					TableColumn tccostDay = tableviewCost.getColumns().get(6);
					tccostDay.setCellValueFactory(new PropertyValueFactory<>("reciveDay"));
					tccostDay.setStyle("-fx-alignment: CENTER");
					TableColumn tcreceive = tableviewCost.getColumns().get(7);
					tcreceive.setCellValueFactory(new PropertyValueFactory<>("receive"));
					tcreceive.setStyle("-fx-alignment: CENTER");

					t2Listdata.clear();
					tableviewCost.setItems(t2Listdata);

					ArrayList<AdminCost> dbArrayList = AdminCostDAO.getcostData(id, id2);

					for (AdminCost admincost : dbArrayList) {

						t2Listdata.add(admincost);
					}

					textDong.setText(id);
					textDong.setDisable(true);
					textHo.setText(id2);
					textHo.setDisable(true);

					
					imageQue.setVisible(true);
					imageQue.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							callAlert("��������� ����: ���� 10,000�� \n û�Һ� 10,000�� \n �����ü� ���� 10,000��");
							
						}

					});
					
					
					
					// ���⼼ ��Ʈ
					elecChart.setOnAction((e) -> {
						XYChart.Series series = new XYChart.Series<>();

						try {
							Stage elecchartstage = new Stage(StageStyle.UTILITY);
							elecchartstage.initModality(Modality.WINDOW_MODAL);
							elecchartstage.initOwner(primaryStage);
							elecchartstage.setTitle("���⼼ ");
							FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/elecchart.fxml"));

							Parent root1 = loader1.load();

							Button btnClose = (Button) root1.lookup("#btnClose");
							LineChart<?, ?> elecChart1 = (LineChart<?, ?>) root1.lookup("#elecChart1");

							ArrayList<AdminCost> dbArrayList1 = AdminCostDAO.getchart();

							for (AdminCost admincost : dbArrayList1) {
								int total1 = Integer.parseInt(admincost.getElectric());
								String day1 = admincost.getReciveDay();

								series.getData().add(new XYChart.Data(day1, total1));
							}

							elecChart1.getData().addAll(series);

							
							btnClose.setOnMouseClicked((e3) -> {
								elecchartstage.close();

							});

							Scene scene = new Scene(root1);
							elecchartstage.setScene(scene);
							elecchartstage.show();

						} catch (IOException e1) {
							e1.printStackTrace();
						}

					});

					// ������ ��Ʈ
					waterChart.setOnAction((e2) -> {

						try {
							Stage waterchartstage = new Stage(StageStyle.UTILITY);
							waterchartstage.initModality(Modality.WINDOW_MODAL);
							waterchartstage.initOwner(primaryStage);
							waterchartstage.setTitle("������ ");
							FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../View/waterchart.fxml"));

							Parent root2 = loader2.load();

							Button waterBtnClose = (Button) root2.lookup("#waterBtnClose");
							LineChart waterChart1 = (LineChart) root2.lookup("#waterChart1");

							ArrayList<AdminCost> watercostlist = new ArrayList<>();
							XYChart.Series series1 = new XYChart.Series();

							watercostlist = AdminCostDAO.getchart();

							for (AdminCost admincost : watercostlist) {
								int total2 = Integer.parseInt(admincost.getWater());
								String day2 = admincost.getReciveDay();

								series1.getData().add(new XYChart.Data(day2, total2));

							}
							waterChart1.getData().addAll(series1);

							waterBtnClose.setOnMouseClicked((e3) -> {
								waterchartstage.close();

							});
							Scene scene = new Scene(root2);
							waterchartstage.setScene(scene);
							waterchartstage.show();

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});

					// ������
					gasChart.setOnAction((e2) -> {

						try {
							Stage gaschartstage = new Stage(StageStyle.UTILITY);
							gaschartstage.initModality(Modality.WINDOW_MODAL);
							gaschartstage.initOwner(primaryStage);
							gaschartstage.setTitle("������ ");
							FXMLLoader loader3 = new FXMLLoader(getClass().getResource("../View/gaschart.fxml"));

							Parent root3 = loader3.load();

							Button gasBtnClose = (Button) root3.lookup("#gasBtnClose");
							LineChart gasChart1 = (LineChart) root3.lookup("#gasChart1");

							ArrayList<AdminCost> gascostlist = new ArrayList<>();
							XYChart.Series series2 = new XYChart.Series();

							gascostlist = AdminCostDAO.getchart();

							for (AdminCost admincost : gascostlist) {
								int total = Integer.parseInt(admincost.getGas());
								String day = admincost.getReciveDay();

								series2.getData().add(new XYChart.Data(day, total));

							}
							gasChart1.getData().addAll(series2);

							gasBtnClose.setOnMouseClicked((e3) -> {
								gaschartstage.close();

							});
							Scene scene = new Scene(root3);
							gaschartstage.setScene(scene);
							gaschartstage.show();

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});

					// �����
					heatChart.setOnAction((e2) -> {

						try {
							Stage heatchartstage = new Stage(StageStyle.UTILITY);
							heatchartstage.initModality(Modality.WINDOW_MODAL);
							heatchartstage.initOwner(primaryStage);
							heatchartstage.setTitle("����� ");
							FXMLLoader loader4 = new FXMLLoader(getClass().getResource("../View/heatchart.fxml"));

							Parent root4 = loader4.load();

							Button heatBtnClose = (Button) root4.lookup("#heatBtnClose");
							LineChart heatChart1 = (LineChart) root4.lookup("#heatChart1");

							ArrayList<AdminCost> heatcostlist = new ArrayList<>();
							XYChart.Series series3 = new XYChart.Series();

							heatcostlist = AdminCostDAO.getchart();

							for (AdminCost admincost : heatcostlist) {
								int total = Integer.parseInt(admincost.getHeat());
								String day = admincost.getReciveDay();

								series3.getData().add(new XYChart.Data(day, total));

							}
							heatChart1.getData().addAll(series3);

							heatBtnClose.setOnMouseClicked((e3) -> {
								heatchartstage.close();

							});
							Scene scene = new Scene(root4);
							heatchartstage.setScene(scene);
							heatchartstage.show();

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});

					btnOk.setOnAction((e) -> {
						citizenstage.close();

					});

					Scene scene = new Scene(root);
					citizenstage.setScene(scene);
					citizenstage.show();

					
					tableviewCost.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							//pie ��Ʈ
							if (event.getClickCount() == 2) {
								try {
									AdminCost selectCost = tableviewCost.getSelectionModel().getSelectedItem();
									Stage bcStage = new Stage(StageStyle.UTILITY);
									bcStage.initModality(Modality.WINDOW_MODAL);
									bcStage.initOwner(primaryStage); 
									bcStage.setTitle("PieChart");

									FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/avgchart.fxml"));
									Parent root;

									root = loader.load();
									PieChart pieChart = (PieChart) root.lookup("#pieChart1");
									Button charBtnclose = (Button) root.lookup("#charBtnclose");

									System.out.println(tableviewCost.getSelectionModel().getSelectedItem().getElectric());
									System.out.println(tableviewCost.getSelectionModel().getSelectedItem().getGas());
									System.out.println(tableviewCost.getSelectionModel().getSelectedItem().getHeat());
									
	
									pieChart.setData(FXCollections.observableArrayList(
											new PieChart.Data("���⼼",  Integer.parseInt(selectCost.getElectric())),
											new PieChart.Data("������",  Integer.parseInt(selectCost.getGas())),
											
											new PieChart.Data("�����", Integer.parseInt(selectCost.getHeat())),
											new PieChart.Data("������", Integer.parseInt(selectCost.getWater()))
											));
									
									
									charBtnclose.setOnAction(e1 -> bcStage.close());

									Scene scene = new Scene(root);
									bcStage.setScene(scene);
									bcStage.show();

								} catch (IOException e1) {
									e1.printStackTrace();
								}

							}		
							
						}
					});
					
					
					
					
					
					
					
					
					
					
					
					
				} else {
					callAlert("�α��� �Ұ�:���̵�� ��й�ȣ Ȯ�� �ٶ��ϴ�.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// �ݱ��ư Ŭ���� ����
	private void handlerBtnCAction() {
		Platform.exit();
	}

	// ȸ������â ���
	private void handlerBtnjoinAction() {

		try {
			Stage citizensignstage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/signup.fxml"));
			Parent root = loader.load();

			SignUpController signUpController = loader.getController();
			signUpController.citizensignstage = citizensignstage;

			Scene scene = new Scene(root);
			citizensignstage.setScene(scene);
			citizensignstage.show();
			callAlert("ȸ������ â�Դϴ�. :��ĭ���� ��� �ۼ����ּ���");
		} catch (IOException e1) {

			callAlert("ȸ������â ���� : ȭ���� ������ ���� �α����� ���ѵǾ����ϴ�. \n ���˹ٶ��ϴ�.");
			e1.printStackTrace();
		}

	}

//��Ÿ: �˸�â
	private void callAlert(String contentText) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("���!");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));

		alert.showAndWait();
	}




}
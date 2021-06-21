package application.controller.student;

import java.io.IOException;
import application.SceneHandler;
//import application.student.ScheduledGetSufficientVotes;
//import application.student.ScheduledGetUnsufficientVotes;
import application.student.ScheduledGetVotes;
//import application.student.ScheduledGetWaitingVotes;
import application.student.StudentUtil;
import application.student.VotesTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PerformanceStudentPageController 
{
	private ScheduledGetVotes refreshVotes= new ScheduledGetVotes();
	private ScheduledGetVotes refreshGraphic= new ScheduledGetVotes();
	//private ScheduledGetUnsufficientVotes refreshUnsufficient= new ScheduledGetUnsufficientVotes();
	//private ScheduledGetSufficientVotes refreshSufficient= new ScheduledGetSufficientVotes();
	//private ScheduledGetWaitingVotes refreshWaiting= new ScheduledGetWaitingVotes();
	private boolean firstRefreshGraphic=true;
	
	
	
	@FXML
    private ImageView logoView;
    @FXML
    private Label votesAverange;
    @FXML
    private BorderPane averangeBorderPane;

    @FXML
    private VBox vBoxContainer;
	
    @FXML
    private Label unsufficientLabel;

    @FXML
    private Label waitingVotesLabel;

    @FXML
    private Label sufficientLabel;
    @FXML
    private TableView<VotesTableModel> tableView;

    @FXML
    private TableColumn<VotesTableModel, String> nameColumn;

    @FXML
    private TableColumn<VotesTableModel, Integer> voteColumn;
    
    private ObservableList<VotesTableModel> votes=FXCollections.observableArrayList();

    @FXML
    private Button backButton;



   
    @FXML
    void backClicked(ActionEvent event) 
    {
    	try 
    	{
			SceneHandler.getInstance().setStudentHomePage();
		} 
    	catch (IOException e) 
    	{
			System.out.println(StudentUtil.BACKTOHOMEPROBLEM);
		}

    	
    }
    
    @FXML
    void initialize()
    {

    	startTableRefresh();	
    	startGrapichRefresh();
    	logoView.imageProperty().set(new Image(getClass().getResourceAsStream("/loginResources/logoLogin.jpg"))); 
    	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	voteColumn.setCellValueFactory(new PropertyValueFactory<>("vote"));
    	
    }
    
    private void startGrapichRefresh()
    {
    	refreshGraphic.setPeriod(Duration.seconds(15));
    	   
    	refreshGraphic.setDelay(Duration.seconds(0.1));

    	refreshGraphic.setOnSucceeded(new EventHandler<WorkerStateEvent>() 
    	{
    		

			@Override
			public void handle(WorkerStateEvent event) 
			{
				CategoryAxis xAxis= new CategoryAxis();
				NumberAxis yAxis= new NumberAxis();
				BarChart<String, Number> votesGraphic= new BarChart<>(xAxis, yAxis);
				XYChart.Series<String,Number> graphicData=new XYChart.Series<String,Number>();
				votesGraphic.setTitle("Andamento dei voti");
				xAxis.setLabel(StudentUtil.OBJECTPERFORMANCE);
				yAxis.setLabel(StudentUtil.VOTEPERFORMANCE);
				ObservableList<VotesTableModel> votes= (ObservableList<VotesTableModel>) event.getSource().getValue();
				Float averange=(float) 0.0;
				int size=0;
				//ora mi riempio le barre del grafico 
				if(!(votes==null))
				{	
					for(VotesTableModel v: votes)
					{
							if(v.getVote().equals(StudentUtil.VOTEABSENT))
							{
								graphicData.getData().add(new XYChart.Data<String, Number>(v.getName(), 0));
							}
							else 
							{
								try 
								 {
								    Integer voto = Integer.parseInt(v.getVote());
								    graphicData.getData().add(new XYChart.Data<String, Number>(v.getName(), voto));
								    averange+=voto;
								    size++;
									  
								 }
								 catch (NumberFormatException e) 
								 {
									    
									  
								 }
							}
		
					}
					if(size!=0)
					{
						averange/=size;
						votesAverange.setText(averange.toString());
						if(averange>=6)
						{
							averangeBorderPane.setStyle(StudentUtil.SUFFICIENTAVERAGEPANESTYLE);
						}
						else
						{
							averangeBorderPane.setStyle(StudentUtil.INSUFFICIENTAVERAGEPANESTYLE);
					
						}
							
					}
					else
					{
						votesAverange.setText("0.0");
						averangeBorderPane.setStyle(StudentUtil.NULLAVERAGEPANESTYLE);
					}
					
					votesGraphic.getData().add(graphicData);
					if(firstRefreshGraphic)
					{
						vBoxContainer.getChildren().add(1, votesGraphic);
						firstRefreshGraphic=false;
						
					}
					else
					{
						vBoxContainer.getChildren().remove(1);
						vBoxContainer.getChildren().add(1, votesGraphic);
					}
					
				}
					
				
				
				
			}
    		
		});

	   
    	refreshGraphic.start();
    	
    }
    
    
    private void startTableRefresh()
    {
    	refreshVotes.setPeriod(Duration.seconds(15));
  	   
    	refreshVotes.setDelay(Duration.seconds(0.2));

    	refreshVotes.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) 
			{
				Integer unsufficient=0;
				Integer sufficient=0;
				Integer waiting=0;
				votes= (ObservableList<VotesTableModel>) event.getSource().getValue();
				tableView.setItems(votes);
				if(!(votes==null))
				{
					for(VotesTableModel v: votes)
					{
						if(v.getVote().equals(StudentUtil.VOTEABSENT))
						{
							waiting++;
						}
						else 
						{
							try 
							 {
							    Integer vote = Integer.parseInt(v.getVote());
							    if(vote>=6)
							    	sufficient++;
							    else
							    	unsufficient++;
								  
							 }
							 catch (NumberFormatException e) 
							 {
								    
								  
							 }
						}
					}
				}
				
				unsufficientLabel.setText(unsufficient.toString());
				waitingVotesLabel.setText(waiting.toString());
				sufficientLabel.setText(sufficient.toString());
				
			}
    		
		});

	   
    	refreshVotes.start();
    }
    
   
   
    
   
    


}

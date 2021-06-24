package shiftCreater;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import java.util.Calendar;


public class Main extends Application
{
    private static final int WIDTH = 350;  // initial window width
    private static final int HEIGHT = 320; // initial window height
    private static final int MAX_DAYS_IN_MONTH = 31; // max num days in month
    private static final int NUM_WEEK_ROWS = 6; // number of rows for weeks
    private static final int TOP_BAR_SPACING = 10; // spacing in top bar
    private static final String[] dayNames = {
            "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    private MonthYear calendar;
    private Calendar now;
    private Label monthLbl;
    private Label yearLbl;
    private final StackPane[] dayNameCells = new StackPane[dayNames.length];
    //private final Label[] dateLbls = new Label[MAX_DAYS_IN_MONTH];
    private final Button[] dateBtns = new Button[MAX_DAYS_IN_MONTH];
    private final StackPane[] dateCells = new StackPane[dateBtns.length];
    private GridPane datePane;


    @Override
    public void start(Stage stage)
    {
        Button prevMonthBtn;
        Button nextMonthBtn;
        Button prevYearBtn;
        Button nextYearBtn;

        calendar = new MonthYear();
        now = Calendar.getInstance(); // get current date time
        BorderPane displayPane = new BorderPane();

        Font headingFont = Font.font("sans-serif",
                FontWeight.BOLD,
                FontPosture.REGULAR,
                16);

        // month button
        prevMonthBtn = new Button("<");
        prevMonthBtn.setOnAction(e -> {
            calendar.previousMonth();
            updateDisplay();
        });

        // next month button
        nextMonthBtn = new Button(">");
        nextMonthBtn.setOnAction(e -> {
            calendar.nextMonth();
            updateDisplay();
        });

        monthLbl = new Label();
        setMonthLbl();
        monthLbl.setFont(headingFont);
        StackPane monthPane = new StackPane();
        monthPane.getChildren().add(monthLbl);
        HBox monthBox = new HBox(TOP_BAR_SPACING);
        monthBox.getChildren().addAll(prevMonthBtn, monthPane, nextMonthBtn);

        // previous year button
        prevYearBtn = new Button("<");
        prevYearBtn.setOnAction(e -> {
            calendar.previousYear();
            updateDisplay();
        });

        // next year button
        nextYearBtn = new Button(">");
        nextYearBtn.setOnAction(e -> {
            calendar.nextYear();
            updateDisplay();
        });

        yearLbl = new Label();
        setYearLbl();
        yearLbl.setFont(headingFont);
        StackPane yearPane = new StackPane();
        yearPane.getChildren().add(yearLbl);
        HBox yearBox = new HBox(TOP_BAR_SPACING);
        yearBox.getChildren().addAll(prevYearBtn, yearPane, nextYearBtn);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(monthBox);
        topBar.setRight(yearBox);

        datePane = new GridPane();

        for (int i = 0; i < dayNameCells.length; i++)
        {
            dayNameCells[i] = new StackPane();
            dayNameCells[i].getChildren().add(new Label(dayNames[i]));
        }

        for (int i = 0; i < dateBtns.length; i++)
        {
            int date = i+1;
            dateBtns[i] = new Button(String.valueOf(date));
            dateBtns[i].setPrefWidth(40);
            dateBtns[i].setPrefHeight(30);
            dateBtns[i].setOnAction(e -> {
                displayShiftSheet(date);
            });
            dateCells[i] = new StackPane();
            dateCells[i].getChildren().add(dateBtns[i]);
        }

        // percent width for the columns
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100.0/dayNames.length);

        // set the percent column widths
        for (int i = 0; i < dayNames.length; i++)
        {
            datePane.getColumnConstraints().add(column);
        }

        // set percent height for row with day names in it
        double weekDayPercentHeight = 10.0;
        RowConstraints weekRowCons = new RowConstraints();
        weekRowCons.setPercentHeight(weekDayPercentHeight);
        datePane.getRowConstraints().add(weekRowCons);

        // percent heights for the date rows
        RowConstraints rowCons = new RowConstraints();
        rowCons.setPercentHeight((100.0 - weekDayPercentHeight)/NUM_WEEK_ROWS);

        // set the percent heights on the date rows
        for (int i = 1; i <= NUM_WEEK_ROWS; i++)
        {
            datePane.getRowConstraints().add(rowCons);
        }

        datePane.setHgap(1);
        datePane.setVgap(1);

        updateDisplay();

        displayPane.setTop(topBar);
        displayPane.setCenter(datePane);

        Scene scene = new Scene(displayPane, WIDTH, HEIGHT);
        stage.setTitle("Calendar");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void displayShiftSheet(int theDate)
    {
        Shift shift;


        BorderPane mainPane = new BorderPane();

        VBox namePanes = new VBox();
        VBox timeLine = new VBox();
        HBox leftPane = new HBox();
        leftPane.getChildren().addAll(namePanes, timeLine);

        HBox time = new HBox();
        HBox numOfWorkers = new HBox();
        Button submitShift = new Button("Submit your shift");
        submitShift.setOnAction(e -> {
            Stage submit = new Stage();
            Pane p = new Pane();
            TextField txt = new TextField("Your name, ");
            Button submitBtn = new Button("Submit");
            submitBtn.setOnAction(E -> {
                txt.getText();

            });
            p.getChildren().addAll();
        });

        VBox topPane = new VBox();
        topPane.getChildren().addAll(time, numOfWorkers);

        mainPane.setLeft(leftPane);
        mainPane.setTop(topPane);

        namePanes.getChildren().add(new Label("workers' name"));
        timeLine.getChildren().add(new Label("9:00 ~ 15:00"));

        time.getChildren().add(new Text("6時"));





        Stage shiftStage = new Stage();
        shiftStage.setTitle(calendar.getMonth()+1 + "/" + theDate
                + "(" + dayNames[(theDate-1+calendar.getFirstWeekDayOfMonth()-1)%7]
                + ")" + ", " + calendar.getYear());
        shiftStage.setResizable(false);
        Scene shiftScene = new Scene(mainPane, 900, 500);
        shiftStage.setScene(shiftScene);
        shiftStage.show();
    }

    private void updateDisplay()
    {
        setMonthLbl();
        setYearLbl();

        // remove all cells from the datePane
        datePane.getChildren().clear();

        // add the dayNames to the first row
        for (int i = 0; i < dayNameCells.length; i++)
        {
            datePane.add(dayNameCells[i], i, 0);
        }

        int columnNum = 1;
        final int SATURDAY = 6;
        final int SUNDAY = 0;

        // add dates to the calender
        for (int i = 0; i < calendar.numDaysInMonth(); i++)
        {
            // get the day of the week
            int whichDay = calendar.getDayOfWeek(i+1)-1;

            // set the background color
            if (i+1==now.get(Calendar.DAY_OF_MONTH) & calendar.getMonth()==now.get(Calendar.MONTH) & calendar.getYear()==now.get(Calendar.YEAR))
                dateCells[i].setStyle("-fx-background-color: #FFFFC0;");
            else
                dateCells[i].setStyle("-fx-background-color: #CCCCFF;");

            // If the day of the week for a cell is Sunday, then set its text color to red
            if (whichDay == SUNDAY)
                dateBtns[i].setTextFill(Color.RED);
            else if (whichDay == SATURDAY) {
                dateBtns[i].setTextFill(Color.GRAY);
            } else
                dateBtns[i].setTextFill(Color.BLACK);

            // adjust the param
            if (whichDay == SUNDAY) columnNum++;
            if (whichDay == SUNDAY & i == 0) columnNum = 1;
            // add dates to the calender

            datePane.add(dateCells[i], whichDay, columnNum);
        }
    }

    private static String getMonthName(int month)
    {
        switch (month)
        {
            case Calendar.JANUARY:   return " January ";
            case Calendar.FEBRUARY:  return "February ";
            case Calendar.MARCH:     return "  March  ";
            case Calendar.APRIL:     return "  April  ";
            case Calendar.MAY:       return "   May   ";
            case Calendar.JUNE:      return "  June   ";
            case Calendar.JULY:      return "  July   ";
            case Calendar.AUGUST:    return " August  ";
            case Calendar.SEPTEMBER: return "September";
            case Calendar.OCTOBER:   return " October ";
            case Calendar.NOVEMBER:  return "November ";
            case Calendar.DECEMBER:  return "December ";
            default: return "";
        }
    }

    private void setMonthLbl()
    {
        monthLbl.setText(getMonthName(calendar.getMonth()));
    }

    private void setYearLbl()
    {
        yearLbl.setText(String.valueOf(calendar.getYear()));
    }

    public static void main(String[] args) { Application.launch(args); }
}

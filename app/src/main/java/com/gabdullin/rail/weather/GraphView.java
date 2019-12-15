package com.gabdullin.rail.weather;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphView extends View {

    private Paint paintForText;
    private Paint paintForLines;
    private Paint paintForRect;
    private Paint paintForDots;

    private int colorForLines;
    private int colorForDots;
    private int colorForText;
    private int height;
    private int width;

    public static final int NUMBER_OF_VERT_LINES = 9;

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GraphView, 0,0);
        setHeight(typedArray.getInt(R.styleable.GraphView_cv_height, 400));
        setWidth(typedArray.getInt(R.styleable.GraphView_cv_width, 1000));
        setColorForLines(typedArray.getInt(R.styleable.GraphView_line_color,Color.BLACK));
        setColorForDots(typedArray.getInt(R.styleable.GraphView_dots_color, Color.BLACK));
        setColorForText(typedArray.getInt(R.styleable.GraphView_text_color, Color.BLACK));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaints();

        float stepBetweenLines = width / 8;

        /*        canvas.drawRect(0,200, 1100, 800, paintForRect);*/
        float [] graphLine = new float[NUMBER_OF_VERT_LINES * 4];

        //В цикле задаем в массив координат для точек начала и конца каждой вертикальной линии.
        //Для каждой линии 4 элемента массива

        float countOfStepsLines = 0;
        for (int xUp = 0, xDown = 2, yUp = 1, yDown = 3; xUp < NUMBER_OF_VERT_LINES * 4 ; xUp = xUp +4, countOfStepsLines = countOfStepsLines + stepBetweenLines, xDown = xDown + 4, yUp = yUp + 4, yDown = yDown + 4 ){
            graphLine[xUp] = countOfStepsLines;
            graphLine[xDown] = countOfStepsLines;
            graphLine[yUp] = 0;
            graphLine[yDown] = height;
        }

        //В цикле генерируем координаты точек для температуры. Пока они задаются рандомно.
        //Шаг совпадает с шагом линий
        //Здесь же в цикле рандомные значения точек преобразуем в значения температур и складываем в отельный массив
        float countOfStepsDots = 0;
        float [] points = new float[NUMBER_OF_VERT_LINES * 2];
        float[] temperature = new float[NUMBER_OF_VERT_LINES];
        for (int i = 0, j=1, m = 0; i < NUMBER_OF_VERT_LINES * 2; i = i+2, j = j + 2, countOfStepsDots = countOfStepsDots + stepBetweenLines, m++){
            points[i] = countOfStepsDots;
            points[j] = (int) ((Math.random() * height));
            temperature[m] = Math.abs(height - points[j])/40;
        }

        //Создаем массив с подписями времени
        String [] dayTimeline = new String[NUMBER_OF_VERT_LINES];
        for(int i = 0, j = 0; i < NUMBER_OF_VERT_LINES; i++, j = j + 3){
            dayTimeline[i] = "" + j + ":00";
        }

        //Вертикальные линии в сетке
        canvas.drawLines(graphLine,0,NUMBER_OF_VERT_LINES * 4, paintForLines);

        //Координатная ось Х (таймлайн)
        canvas.drawLine(0,height,width,height, paintForLines);

        //Точки температуры
        canvas.drawPoints(points, paintForDots);
        //Линия, соединяющая четные точки
        canvas.drawLines(points, paintForLines);
        //Линия, соединяющая нечетные точки
        canvas.drawLines(points,2,NUMBER_OF_VERT_LINES * 2 - 2, paintForLines);

        //Текст подписей температурных значений
        for(int i = 0, j = 1, m = 0; i < NUMBER_OF_VERT_LINES * 2; i = i + 2, j = j + 2, m++ ){
            canvas.drawText("+" + Math.round(temperature[m])+"°", points[i] + 25, points[j] + 25, paintForText);
        }

        //Подписи таймлайна
        for(int i = 0, j = 1, m = 0; i < NUMBER_OF_VERT_LINES * 2; i = i + 2, j = j + 2, m++ ){
            canvas.drawText(dayTimeline[m], points[i] - 30, height + 50, paintForText);
        }
    }

    private void initPaints() {
        paintForText = new Paint();
        paintForText.setStyle(Paint.Style.FILL);
        paintForText.setColor(colorForText);
        paintForText.setStrokeWidth(7);
        paintForText.setTextSize(40);

        paintForLines = new Paint();
        paintForLines.setStyle(Paint.Style.FILL_AND_STROKE);
        paintForLines.setColor(colorForLines);
        paintForLines.setStrokeWidth(3);

        paintForDots = new Paint();
        paintForDots.setStyle(Paint.Style.FILL_AND_STROKE);
        paintForDots.setColor(colorForDots);
        paintForDots.setStrokeWidth(22);
    }

    public void setColorForLines(int colorForLines) {
        this.colorForLines = colorForLines;
    }

    public void setColorForDots(int colorForDots) {
        this.colorForDots = colorForDots;
    }

    public void setColorForText(int colorForText) {
        this.colorForText = colorForText;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

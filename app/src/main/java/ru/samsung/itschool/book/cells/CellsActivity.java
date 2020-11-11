package ru.samsung.itschool.book.cells;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.GridLayout;


import task.Stub;
import task.Task;

public class CellsActivity extends Activity implements OnClickListener,
        OnLongClickListener {

    private int WIDTH = 4; // Ширина сетки
    private int HEIGHT = 5; // Высота сетки

    private Button[][] cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cells);
        makeCells(); // Рисует сетку

    }



    @Override
    public boolean onLongClick(View v) {
        //Эту строку нужно удалить
        return false;
    }

    @Override
    public void onClick(View v) {
        //Эту строку нужно удалить

        Button tappedCell = (Button) v;

        //Получаем координтаты нажатой клетки
        int tappedX = getX(tappedCell);
        int tappedY = getY(tappedCell);

        Button temp = cells[tappedY][tappedX]; // получаем нажатую кнопку как элемент
        swapColor(temp); // Меняем кнопке цвет (Если был жёлтый → белый, если был белый → жёлтый)
        // Проверяем, есть ли по сторонам клетки (сверху, внизу, слева, справа. Крестом)
        // Если есть, меняем им цвет. Если нет, пропускаем.
        if (tappedY - 1 >= 0) {
            swapColor(cells[tappedY - 1][tappedX]);
        }
        if (tappedX - 1 >= 0) {
            swapColor(cells[tappedY][tappedX - 1]);
        }
        if (tappedY + 1 < HEIGHT) {
            swapColor(cells[tappedY + 1][tappedX]);
        }
        if (tappedX + 1 < WIDTH) {
            swapColor(cells[tappedY][tappedX + 1]);
        }



        if(checkWin(cells)) {
            // Если все клетки жёлтые выводим сообщение о победе и увеличиваем поле
            Task.showMessage(this, "Вы выграли.\n Продолжаем игру на большей сетке");
            WIDTH++;
            HEIGHT++;
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    cells[i][j].setBackgroundColor(Color.WHITE); // Чистим поле
                }
            }
            setContentView(R.layout.cells);
            makeCells(); // Отрисовываем поле заново, так как ширина и высота поменялись
        }

    }
    public void swapColor(Button btn) {
        int color = ((ColorDrawable)btn.getBackground()).getColor();
        btn.setBackgroundColor(color == Color.YELLOW ? Color.WHITE : Color.YELLOW);
    }
    public boolean checkWin(Button[][] cells) {
        boolean isWin = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length ; j++) {
                if (((ColorDrawable)cells[i][j].getBackground()).getColor() == Color.WHITE) {
                    isWin = false;
                }
            }
        }

        return isWin;
    };
	/*
     * NOT FOR THE BEGINNERS
	 * ==================================================
	 */

    int getX(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[1]);
    }

    int getY(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[0]);
    }

    void makeCells() {
        cells = new Button[HEIGHT][WIDTH];
        GridLayout cellsLayout = (GridLayout) findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(WIDTH);
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                cells[i][j] = (Button) inflater.inflate(R.layout.cell, cellsLayout, false);
                cells[i][j].setOnClickListener(this);
                cells[i][j].setOnLongClickListener(this);
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
    }

}
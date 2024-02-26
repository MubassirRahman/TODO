package com.example.todo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.Task;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private EditText editTextTaskName;
    private EditText editTextDueDate;
    private CheckBox checkBoxCompleted;
    private Button buttonAdd;
    private Button buttonDeleteCompleted;
    private ListView listViewTasks;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> adapter;
    private Task selectedTask; // To store the selected task for editing


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        checkBoxCompleted = findViewById(R.id.checkBoxCompleted);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDeleteCompleted = findViewById(R.id.buttonDeleteCompleted);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, R.layout.task_item, taskList);
        listViewTasks.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        buttonDeleteCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCompletedTasks();
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggleTaskCompletion(position);
            }
        });


        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTask(position);
                return true;
            }
        });
    }

    private void addTask() {
        String taskName = editTextTaskName.getText().toString().trim();
        String dueDateString = editTextDueDate.getText().toString().trim();
        boolean completed = checkBoxCompleted.isChecked();

        if (!taskName.isEmpty()) {
            Date dueDate = parseDueDate(dueDateString);

            if (selectedTask == null) {
                // Add new task
                Task task = new Task(taskName, dueDate, completed);
                taskList.add(task);
            } else {
                // Update existing task
                selectedTask.setTaskName(taskName);
                selectedTask.setDueDate(dueDate);
                selectedTask.setCompleted(completed);
                selectedTask = null; // Reset selected task after update
                buttonAdd.setText("Add Task"); // Change button text back to default
            }

            sortTasks();
            adapter.notifyDataSetChanged();
            clearInputFields();
        } else {
            Toast.makeText(this, "Task name cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleTaskCompletion(int position) {
        Task task = taskList.get(position);
        task.setCompleted(!task.isCompleted());
        adapter.notifyDataSetChanged();
    }
    private void deleteTask(int position) {
        taskList.remove(position);
        adapter.notifyDataSetChanged();
        clearInputFields();
    }

    private void deleteCompletedTasks() {
        for (int i = taskList.size() - 1; i >= 0; i--) {
            if (taskList.get(i).isCompleted()) {
                taskList.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
        clearInputFields();
    }

    private void clearInputFields() {
        editTextTaskName.getText().clear();
        editTextDueDate.getText().clear();
        checkBoxCompleted.setChecked(false);
    }

    private void sortTasks() {
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                if (task1.getDueDate() == null || task2.getDueDate() == null) {
                    return 0;
                }
                return task1.getDueDate().compareTo(task2.getDueDate());
            }
        });
    }

    private Date parseDueDate(String dueDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
            return dateFormat.parse(dueDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String dateToString(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            return dateFormat.format(date);
        }
        return "";
    }


}

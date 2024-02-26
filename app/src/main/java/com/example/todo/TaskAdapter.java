package com.example.todo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        Task task = getItem(position);

        if (task != null) {
            TextView textViewTaskName = convertView.findViewById(R.id.textViewTaskName);
            TextView textViewDueDate = convertView.findViewById(R.id.textViewDueDate);
            CheckBox checkBoxCompleted = convertView.findViewById(R.id.checkBoxCompleted);

            textViewTaskName.setText(task.getTaskName());
            textViewDueDate.setText(dateToString(task.getDueDate()));
            checkBoxCompleted.setChecked(task.isCompleted());
            checkBoxCompleted.setText(task.isCompleted() ? "Completed" : "Not Completed");
        }

        return convertView;
    }

    private String dateToString(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            return dateFormat.format(date);
        }
        return "";
    }
}

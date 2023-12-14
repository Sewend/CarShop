package com.example.carshop;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class Car {
    private String name;
    private String description;
    private Bitmap image;
    private int quantity;

    public Car(String name, String description, Bitmap image, int quantity) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class CarAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Car> cars;

    public CarAdapter(Context context, ArrayList<Car> cars) {
        this.context = context;
        this.cars = cars;
    }
    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Object getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.car_item, null);
        ImageView imageView = view.findViewById(R.id.car_image);
        TextView nameView = view.findViewById(R.id.car_name);
        TextView descriptionView = view.findViewById(R.id.car_description);
        TextView quantityView = view.findViewById(R.id.car_quantity);
        Button increaseButton = view.findViewById(R.id.increase_button);
        Button decreaseButton = view.findViewById(R.id.decrease_button);
        Button deleteButton = view.findViewById(R.id.delete_button);
        Car car = cars.get(position);
        imageView.setImageBitmap(car.getImage());
        nameView.setText(car.getName());
        descriptionView.setText(car.getDescription());
        quantityView.setText(String.valueOf(car.getQuantity()));
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car.setQuantity(car.getQuantity() + 1);
                quantityView.setText(String.valueOf(car.getQuantity()));
            }
        });
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (car.getQuantity() > 0) {
                    car.setQuantity(car.getQuantity() - 1);
                    quantityView.setText(String.valueOf(car.getQuantity()));
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cars.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Товар удален", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
public class MainActivity extends Activity {
    private ListView listView;
    private CarAdapter adapter;
    private ArrayList<Car> cars;
    private Button addButton;
    private EditText nameInput;
    private EditText descriptionInput;
    private EditText quantityInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.car_list);
        addButton = findViewById(R.id.add_button);
        nameInput = findViewById(R.id.name_input);
        descriptionInput = findViewById(R.id.description_input);
        quantityInput = findViewById(R.id.quantity_input);
        cars = new ArrayList<>();
        cars.add(new Car("BMW X5", "Спортивный внедорожник", BitmapFactory.decodeResource(getResources(), R.drawable.bmw_x5), 10));
        cars.add(new Car("Audi A6", "Бизнес-седан", BitmapFactory.decodeResource(getResources(), R.drawable.audi_a6), 5));
        cars.add(new Car("Tesla Model 3", "Электромобиль", BitmapFactory.decodeResource(getResources(), R.drawable.tesla_model_3), 3));
        adapter = new CarAdapter(this, cars);
        listView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String quantity = quantityInput.getText().toString();
                if (!name.isEmpty() && !description.isEmpty() && !quantity.isEmpty()) {
                    int quantityInt = Integer.parseInt(quantity);
                    Car newCar = new Car(name, description, BitmapFactory.decodeResource(getResources(), R.drawable.car_default), quantityInt);
                    cars.add(newCar);
                    adapter.notifyDataSetChanged();
                    nameInput.setText("");
                    descriptionInput.setText("");
                    quantityInput.setText("");
                    Toast.makeText(MainActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



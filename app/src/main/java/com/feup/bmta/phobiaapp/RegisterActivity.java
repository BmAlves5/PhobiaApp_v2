package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import Bio.Library.namespace.BioLib;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, idCardEditText, newUsernameEditText, newPasswordEditText;
    private DatePicker datePicker;
    private RadioGroup genderRadioGroup;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        idCardEditText = findViewById(R.id.idCardEditText);
        newUsernameEditText = findViewById(R.id.newUsernameEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        datePicker = findViewById(R.id.datePicker);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        Button registerButton = findViewById(R.id.registerUserButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obter os valores inseridos pelos usuários
                String fullName = fullNameEditText.getText().toString().trim();
                String idCardNumber = idCardEditText.getText().toString().trim();
                String username = newUsernameEditText.getText().toString().trim();
                String password = newPasswordEditText.getText().toString().trim();

                // Obter a data de nascimento do DatePicker
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                String dateOfBirth = day + "/" + (month + 1) + "/" + year;

                // Obter o gênero selecionado
                int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);

                // Chame o método addUser para inserir o usuário na base de dados
                long userId = dbHelper.addUser(username, password, fullName, dateOfBirth, selectedGenderRadioButton.getText().toString(), idCardNumber);

                if (isValidRegistration(fullName, dateOfBirth, selectedGenderRadioButton, idCardNumber, username, password)) {
                    // Abra a conexão com o banco de dados
                    dbHelper.openDatabase();

                    // Crie uma intenção para iniciar a nova atividade
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                    // Passe os dados do usuário para a nova atividade
                    intent.putExtra("FULL_NAME", fullName);
                    intent.putExtra("DATE_OF_BIRTH", dateOfBirth);
                    intent.putExtra("GENDER", selectedGenderRadioButton.getText().toString());
                    intent.putExtra("ID_CARD_NUMBER", idCardNumber);
                    intent.putExtra("USERNAME", username);

                    // Inicie a nova atividade
                    startActivity(intent);

                    Toast.makeText(RegisterActivity.this, "Successful registration!", Toast.LENGTH_SHORT).show();
                    finish(); // Finaliza a atividade após o registro bem-sucedido
                }
            }
        });
    }

    private boolean isValidRegistration(String fullName, String dateOfBirth, RadioButton selectedGenderRadioButton,
                                        String idCardNumber, String username, String password) {
        // Adicione suas próprias verificações de validação aqui
        // Este é apenas um exemplo básico
        return !fullName.isEmpty() && !dateOfBirth.isEmpty() && selectedGenderRadioButton != null &&
                isValidIdCardNumber(idCardNumber) && !username.isEmpty() && !password.isEmpty();
    }

    private boolean isValidIdCardNumber(String idCardNumber) {
        // Verifica se o número do cartão de cidadão tem 8 dígitos
        if (idCardNumber.length() != 8 || !idCardNumber.matches("\\d+")) {
            // Mostra um aviso ao usuário
            Toast.makeText(this, "The citizen card number must be exactly 8 digits long.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
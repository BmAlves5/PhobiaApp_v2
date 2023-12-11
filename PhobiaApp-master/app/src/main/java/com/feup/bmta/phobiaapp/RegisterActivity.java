
package com.feup.bmta.phobiaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, idCardEditText, usernameEditText, passwordEditText;
    private DatePicker datePicker;
    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        idCardEditText = findViewById(R.id.idCardEditText);
        usernameEditText = findViewById(R.id.newUsernameEditText);
        passwordEditText = findViewById(R.id.newPasswordEditText);
        datePicker = findViewById(R.id.datePicker);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        Button registerButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obter os valores inseridos pelos usuários
                String fullName = fullNameEditText.getText().toString().trim();
                String idCardNumber = idCardEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Obter a data de nascimento do DatePicker
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                String dateOfBirth = day + "/" + (month + 1) + "/" + year;

                // Obter o gênero selecionado
                int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);

                if (isValidRegistration(fullName, dateOfBirth, selectedGenderRadioButton, idCardNumber, username, password)) {
                    // Faça o que for necessário para registrar o usuário
                    // Por exemplo, enviar dados para um servidor, salvar em um banco de dados local, etc.
                    Toast.makeText(RegisterActivity.this, "Registo bem-sucedido!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "O número do cartão de cidadão deve ter exatamente 8 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrscanner.MainActivity;
import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.R;

public class QRGenPhoneActivity extends AppCompatActivity {
    private String raw, result, country, code;
    private EditText qrPhone;
    private Spinner areaSpinner;
    private ImageButton homeBtn;
    private Button genBtn;
    public static String[] countryCode = {
          "(+7840) Abkhazia", "(+93) Afghanistan", "(+355) Albania", "(+213) Algeria", "(+1684) American Samoa"
        , "(+376) Andorra", "(+244) Angola", "(+1264) Anguilla", "(+1268) Antigua and Barbuda", "(+54) Argentina"
        , "(+374) Armenia", "(+297) Aruba", "(+247) Ascension", "(+61) Australia"
        , "(+672) Australian External Territories", "(+43) Austria", "(+994) Azerbaijan", "(+1242) Bahamas"
        , "(+973) Bahrain", "(+880) Bangladesh", "(+1246) Barbados", "(+1268) Barbuda", "(+375) Belarus"
        , "(+32) Belgium", "(+501) Belize", "(+229) Benin", "(+1441) Bermuda", "(+975) Bhutan", "(+591) Bolivia"
        , "(+387) Bosnia and Herzegovina", "(+267) Botswana", "(+55) Brazil", "(+246) British Indian Ocean Territory"
        , "(+1284) British Virgin Islands", "(+673) Brunei", "(+359) Bulgaria", "(+226) Burkina Faso"
        , "(+257) Burundi", "(+855) Cambodia", "(+237) Cameroon", "(+1) Canada", "(+238) Cape Verde"
        , "(+345) Cayman Islands", "(+236) Central African Republic", "(+235) Chad", "(+56) Chile", "(+86) China"
        , "(+61) Christmas Island", "(+61) Cocos-Keeling Islands", "(+57) Colombia", "(+269) Comoros", "(+242) Congo"
        , "(+243) Congo, Dem. Rep. of (Zaire)", "(+682) Cook Islands", "(+506) Costa Rica", "(+385) Croatia"
        , "(+53) Cuba", "(+599) Curacao", "(+537) Cyprus", "(+420) Czech Republic", "(+45) Denmark"
        , "(+246) Diego Garcia", "(+253) Djibouti", "(+1767) Dominica", "(+1809) Dominican Republic"
        , "(+670) East Timor", "(+56) Easter Island", "(+593) Ecuador", "(+20) Egypt", "(+503) El Salvador"
        , "(+240) Equatorial Guinea", "(+291) Eritrea", "(+372) Estonia", "(+251) Ethiopia", "(+500) Falkland Islands"
        , "(+298) Faroe Islands", "(+679) Fiji", "(+358) Finland", "(+33) France", "(+596) French Antilles"
        , "(+594) French Guiana", "(+689) French Polynesia", "(+241) Gabon", "(+220) Gambia", "(+995) Georgia"
        , "(+49) Germany", "(+233) Ghana", "(+350) Gibraltar", "(+30) Greece", "(+299) Greenland", "(+1473) Grenada"
        , "(+590) Guadeloupe", "(+1671) Guam", "(+502) Guatemala", "(+224) Guinea", "(+245) Guinea-Bissau"
        , "(+595) Guyana", "(+509) Haiti", "(+504) Honduras", "(+852) Hong Kong SAR China", "(+36) Hungary"
        , "(+354) Iceland", "(+91) India", "(+62) Indonesia", "(+98) Iran", "(+964) Iraq", "(+353) Ireland"
        , "(+972) Israel", "(+39) Italy", "(+225) Ivory Coast", "(+1876) Jamaica", "(+81) Japan", "(+962) Jordan"
        , "(+77) Kazakhstan", "(+254) Kenya", "(+686) Kiribati", "(+965) Kuwait", "(+996) Kyrgyzstan", "(+856) Laos"
        , "(+371) Latvia", "(+961) Lebanon", "(+266) Lesotho", "(+231) Liberia", "(+218) Libya"
        , "(+423) Liechtenstein", "(+370) Lithuania", "(+352) Luxembourg", "(+853) Macau SAR China"
        , "(+389) Macedonia", "(+261) Madagascar", "(+265) Malawi", "(+60) Malaysia", "(+960) Maldives"
        , "(+223) Mali", "(+356) Malta", "(+692) Marshall Islands", "(+596) Martinique", "(+222) Mauritania"
        , "(+230) Mauritius", "(+262) Mayotte", "(+52) Mexico", "(+691) Micronesia", "(+1808) Midway Island"
        , "(+373) Moldova", "(+377) Monaco", "(+976) Mongolia", "(+382) Montenegro", "(+1664) Montserrat"
        , "(+212) Morocco", "(+95) Myanmar", "(+264) Namibia", "(+674) Nauru", "(+977) Nepal", "(+31) Netherlands"
        , "(+599) Netherlands Antilles", "(+1869) Nevis", "(+687) New Caledonia", "(+64) New Zealand"
        , "(+505) Nicaragua", "(+227) Niger", "(+234) Nigeria", "(+683) Niue", "(+672) Norfolk Island"
        , "(+850) North Korea", "(+1670) Northern Mariana Islands", "(+47) Norway", "(+968) Oman", "(+92) Pakistan"
        , "(+680) Palau", "(+970) Palestinian Territory", "(+507) Panama", "(+675) Papua New Guinea"
        , "(+595) Paraguay", "(+51) Peru", "(+63) Philippines", "(+48) Poland", "(+351) Portugal"
        , "(+1787) Puerto Rico", "(+974) Qatar", "(+262) Reunion", "(+40) Romania", "(+7) Russia", "(+250) Rwanda"
        , "(+685) Samoa", "(+378) San Marino", "(+966) Saudi Arabia", "(+221) Senegal", "(+381) Serbia"
        , "(+248) Seychelles", "(+232) Sierra Leone", "(+65) Singapore", "(+421) Slovakia", "(+386) Slovenia"
        , "(+677) Solomon Islands", "(+27) South Africa", "(+500) South Georgia and the South Sandwich Islands"
        , "(+82) South Korea", "(+34) Spain", "(+94) Sri Lanka", "(+249) Sudan", "(+597) Suriname"
        , "(+268) Swaziland", "(+46) Sweden", "(+41) Switzerland", "(+963) Syria", "(+886) Taiwan"
        , "(+992) Tajikistan", "(+255) Tanzania", "(+66) Thailand", "(+670) Timor Leste", "(+228) Togo"
        , "(+690) Tokelau", "(+676) Tonga", "(+1868) Trinidad and Tobago", "(+216) Tunisia", "(+90) Turkey"
        , "(+993) Turkmenistan", "(+1649) Turks and Caicos Islands", "(+688) Tuvalu", "(+1340) U.S. Virgin Islands"
        , "(+256) Uganda", "(+380) Ukraine", "(+971) United Arab Emirates", "(+44) United Kingdom"
        , "(+1) United States", "(+598) Uruguay", "(+998) Uzbekistan", "(+678) Vanuatu", "(+58) Venezuela"
        , "(+84) Vietnam", "(+1808) Wake Island", "(+681) Wallis and Futuna", "(+967) Yemen", "(+260) Zambia"
        , "(+255) Zanzibar", "(+263) Zimbabwe"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_phone);

        areaSpinner = findViewById(R.id.spinner );
        qrPhone = findViewById(R.id.qrPhone);
        homeBtn = findViewById(R.id.genPhoneHomeBtn);
        genBtn = findViewById(R.id.qrPhoneGenBtn);

        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryCode);

        areaSpinner.setAdapter(adapter);
        areaSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selected = adapterView.getItemAtPosition(position).toString();
                String[] res = extractCodeAndCountry(selected);

                if (res != null) {
                    Log.e(" __Code__ ", res[0]);
                    Log.e("__Country__", res[1]);
                    code = res[0];
                    country = res[1];

                } else {
                    Log.e("Invalid code format", "Invalid code format");
                    code = null;
                    country = null;
                }
            }
        });

        genBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrPhone.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_LONG
                    ).show();
                }
                else if (code != null && country != null) {
                    raw = "tel:" + code + String.valueOf(qrPhone.getText());
                    result = code + String.valueOf(qrPhone.getText()) + "," + country;
                    Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                    intent.putExtra("RAW", raw);
                    intent.putExtra("RESULT", result);
                    intent.putExtra("TYPE", 1);
                }
                else {
                    Toast.makeText(
                        getApplicationContext(), "Please choose valid code", Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    public static String[] extractCodeAndCountry(String input) {
        // Regular expression to match the pattern "(+code) Country"
        String regex = "\\((\\+\\d+)\\)\\s*(.+)";

        // Compile the regular expression
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);

        // If the pattern matches, extract the groups
        if (matcher.matches()) {
            String code = matcher.group(1);     // Extracts the code (+680)
            String country = matcher.group(2);  // Extracts the country (Palau)
            return new String[]{code, country};
        }

        // Return null if the input does not match the expected pattern
        return null;
    }
}
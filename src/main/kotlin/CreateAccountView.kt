import com.google.gson.Gson
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tornadofx.Fragment
import tornadofx.View

class CreateAccountView: Fragment() {
    override val root: VBox by fxml()

    @FXML lateinit var accountTextField: TextField
    @FXML lateinit var nameTextField: TextField
    @FXML lateinit var addressTextField: TextField
    @FXML lateinit var postCodeTextField: TextField
    @FXML lateinit var interestRateTextField: TextField

    @FXML lateinit var createButton: Button
    @FXML lateinit var cancelButton: Button

    init {
        createButton.setOnMouseClicked {
            val konto = Konto(null,
            accountTextField.text,
            nameTextField.text,
            addressTextField.text,
            postCodeTextField.text,
            interestRateTextField.text.toInt(),
            0.5)

            val gson = Gson()
            val json = gson.toJson(konto)

            val body: RequestBody = json
                .toRequestBody(null)
            val client = OkHttpClient().newBuilder()
                .build()

            val request: Request = Request.Builder()
                .url("https://frost.met.no/auth/accessToken")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build()
            val response = client.newCall(request).execute()

            val resp: String? = response.body?.string()

            if(response.isSuccessful) {

            }

        }
        cancelButton.setOnMouseClicked {
            close()
        }
       }
}
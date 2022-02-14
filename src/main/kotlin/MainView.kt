import com.google.gson.Gson
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tornadofx.View

class MainView : View() {
    override val root: VBox by fxml()

    @FXML
    lateinit var helloLabel: Label

    @FXML
    lateinit var accountList: ListView<String>

    @FXML
    lateinit var createAccountButton: Button
    lateinit var depositButton: Button
    lateinit var withdrawButton: Button

    init {
        title = "Hello world"
        accountList.items.add("item 1")
        accountList.items.add("item 2")
        accountList.items.add("item 3")
        createAccountButton.setOnMouseClicked {
            val stage = find<CreateAccountView>().openModal()
        }

        getAccounts()
    }

    override fun onBeforeShow() {
        super.onBeforeShow()
        println("onbeforeshow")
    }

    @FXML
    fun getAccounts() {
        val gson = Gson()
        val client = OkHttpClient().newBuilder()
            .build()

        val request: Request = Request.Builder()
            .url("https://frost.met.no/auth/accessToken")
            .method("GET", null)
            .build()
        val response = client.newCall(request).execute()
        val resp: String? = response.body?.string()

        if (response.isSuccessful) {
            val getAccountsResponse = gson.fromJson(resp, GetAccountsResponse::class.java)
            accountList.items.clear()
            accountList.items.addAll(
                getAccountsResponse.items.map { "$it.kontonr - ${it.saldo}" })
        }

    }
}
package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;


public class Controller {

    //以下にController内で使うフィールドを列挙
    //必ずfxml内でfx:idタグと対応付けること
    @FXML
    private Button button;

    @FXML
    private MenuBar menu;

    @FXML
    private TextArea textarea;

    @FXML
    public void Close()
    {
        Platform.exit();
    }

    @FXML
    public void FileOpen()
    {
        //オープンするファイルを選択するダイアログを出す
        //FileChooser...オープンするファイルを選ぶ一般的なダイアログを表示するクラス
        FileChooser filechooser = new FileChooser();
        //ウィンドウのタイトル
        filechooser.setTitle("オープンするファイルを選んでください");
        //開くファイルのフォーマットなど
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("TJA Files", "*.tja"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Alert alert = new Alert(Alert.AlertType.ERROR);//エラーメッセージを表示するダイアログ
        alert.setTitle("Error Message");
        alert.setHeaderText("ERROR!");
        //ファイルを開く
        try {
            //テキストの読み出し処理自体は標準出力とほぼ変わらない
            File file = filechooser.showOpenDialog(null);//ここでダイアログを呼び出す
            FileInputStream fs = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs,"UTF-8"));//文字コードはここではUTF-8
            String s;
            textarea.clear();
            textarea.setText(br.readLine());
            while((s = br.readLine())!=null){
                textarea.setText(textarea.getText()+"\n"+s);
            }
        }catch(FileNotFoundException e){
            alert.setContentText(toStrerror(e));
        }catch (IOException e){
            alert.setContentText(toStrerror(e));
        }//例外はこのように呼び出す
    }

    //エラーのスタックトレースを文字列に変換
    private String toStrerror(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        String str = sw.toString();
        return str;
    }


}

package animaladvertis.com.animaladvertis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;

public class EditAdverticeActivity extends AppCompatActivity {

    @BindView(R.id.richeditor)
    RichEditor richeditor;
    @BindView(R.id.bt_bold)
    Button btBold;
    @BindView(R.id.bt_H1)
    Button btH1;
    @BindView(R.id.bt_H5)
    Button btH5;
    @BindView(R.id.bt_undo)
    Button btUndo;
    @BindView(R.id.bt_redo)
    Button btRedo;
    @BindView(R.id.activity_edit_advertice)
    RelativeLayout activityEditAdvertice;
    @BindView(R.id.tv_preview)
    TextView tvPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_advertice);
        ButterKnife.bind(this);
        richeditor.setFocusable(true);
        richeditor.setEditorHeight(200);
        richeditor.setEditorFontSize(22);
        richeditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                tvPreview.setText(text);
            }
        });
    }

    @OnClick({R.id.bt_bold, R.id.bt_H1, R.id.bt_H5, R.id.bt_undo, R.id.bt_redo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_bold:
                richeditor.setBold();
                break;
            case R.id.bt_H1:
                richeditor.setHeading(1);
                break;
            case R.id.bt_H5:
                richeditor.setHeading(5);
                break;
            case R.id.bt_undo:
                richeditor.undo();
                break;
            case R.id.bt_redo:
                richeditor.redo();
                break;
        }
    }
}

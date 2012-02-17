package chat.namespace;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends Activity {
   TextView tv;
   EditText etext;
   Connection connection;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        etext=(EditText)findViewById(R.id.editText1);
        tv=(TextView)findViewById(R.id.TextView1);
        Button btnSend = (Button)findViewById(R.id.btnSend);
        //imposto scrolling textView
        tv.setMovementMethod(new ScrollingMovementMethod());
        btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv.append("ME: "+etext.getText().toString()+"\n");
				//spedizione messaggio
				Message msg = new Message();
				//al posto di loreti inserire username del destinatario
				msg.setTo("loreti@ppl.eln.uniroma2.it");
				
			}
		});
        try{
        	ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it",5222);
        	config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        	connection = new XMPPConnection(config);
        	connection.connect();
        	connection.login("dipietro", "federico");
        }catch (XMPPException e){
        	e.printStackTrace();
        }
        connection.addPacketListener(new PacketListener() {
			
			@Override
			public void processPacket(Packet pkt) {
				Message msg = (Message)pkt;
				String from = msg.getFrom();
				String body = msg.getBody();
				tv.append(from+" : "+body+"\n");
			}
		}, new MessageTypeFilter(Message.Type.normal));
    }
}
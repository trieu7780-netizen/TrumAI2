package viet.trieule.trumai

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.widget.*
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {
 private val answer = "Tui không biết, xin mời câu hỏi tiếp theo!"
 private lateinit var result: TextView
 private lateinit var question: EditText
 private lateinit var send: Button
 private lateinit var mic: Button
 private var tts: TextToSpeech? = null
 private val handler = Handler(Looper.getMainLooper())
 private var busy = false
 private val speechRequest = 88
 private fun shape(color:Int, radius:Float=24f, stroke:Int?=null):GradientDrawable = GradientDrawable().apply { setColor(color); cornerRadius=radius; if(stroke!=null) setStroke(2,stroke) }
 private fun dp(v:Int)= (v*resources.displayMetrics.density).toInt()
 override fun onCreate(savedInstanceState:Bundle?) { super.onCreate(savedInstanceState)
  window.statusBarColor=Color.rgb(7,14,32); window.navigationBarColor=Color.rgb(7,14,32)
  tts=TextToSpeech(this,this)
  val root=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setBackgroundColor(Color.rgb(7,14,32)); setPadding(dp(22),dp(18),dp(22),dp(16)) }
  val scroll=ScrollView(this).apply { isFillViewport=true; addView(root) }
  setContentView(scroll)
  val icon=ImageView(this).apply { setImageResource(viet.trieule.trumai.R.mipmap.ic_launcher); scaleType=ImageView.ScaleType.FIT_CENTER }
  root.addView(icon,LinearLayout.LayoutParams(-1,dp(180)))
  fun text(s:String, size:Float, color:Int, bold:Boolean=false):TextView=TextView(this).apply { text=s; textSize=size; setTextColor(color); gravity=Gravity.CENTER; if(bold) setTypeface(null,Typeface.BOLD) }
  root.addView(text("TRÙM AI",34f,Color.WHITE,true))
  root.addView(text("HỎI LÀ CÓ KẾT QUẢ",16f,Color.rgb(56,221,255),true).apply { setPadding(0,dp(6),0,dp(26)) })
  question=EditText(this).apply { hint="Bạn muốn hỏi Trùm AI điều gì?"; setHintTextColor(Color.rgb(160,178,199)); setTextColor(Color.WHITE); textSize=17f; minLines=2; maxLines=4; gravity=Gravity.TOP; background=shape(Color.rgb(20,38,67),dp(18).toFloat(),Color.rgb(38,123,165)); setPadding(dp(18),dp(16),dp(18),dp(16)) }
  root.addView(question,LinearLayout.LayoutParams(-1,dp(110)))
  val buttons=LinearLayout(this).apply { orientation=LinearLayout.HORIZONTAL; setPadding(0,dp(16),0,dp(18)) }
  mic=Button(this).apply { text="🎤  NÓI"; isAllCaps=false; textSize=16f; setTextColor(Color.WHITE); background=shape(Color.rgb(28,110,151),dp(16).toFloat()); setOnClickListener { listen() } }
  send=Button(this).apply { text="TÌM KIẾM ✨"; isAllCaps=false; textSize=16f; setTextColor(Color.rgb(7,14,32)); background=shape(Color.rgb(251,213,75),dp(16).toFloat()); setOnClickListener { respond() } }
  buttons.addView(mic,LinearLayout.LayoutParams(0,dp(60),1f).apply { rightMargin=dp(10) }); buttons.addView(send,LinearLayout.LayoutParams(0,dp(60),1.5f)); root.addView(buttons)
  result=text("🤖  Trùm AI đang chờ câu hỏi của bạn...",20f,Color.WHITE,true).apply { background=shape(Color.rgb(17,38,69),dp(20).toFloat(),Color.rgb(44,100,154)); setPadding(dp(16),dp(30),dp(16),dp(30)) }
  root.addView(result,LinearLayout.LayoutParams(-1,-2))
  root.addView(Space(this),LinearLayout.LayoutParams(1,0,1f))
  root.addView(text("© 2026 trieu.le.viet – All Rights Reserved",12f,Color.rgb(157,174,194)).apply { setPadding(0,dp(30),0,dp(4)) })
 }
 private fun respond(){ if(busy)return; busy=true; send.isEnabled=false; mic.isEnabled=false; result.text="🧠  Trùm AI đang suy nghĩ..."; handler.postDelayed({ result.text="🤣  $answer"; tts?.speak(answer,TextToSpeech.QUEUE_FLUSH,null,"trumai_answer"); busy=false; send.isEnabled=true; mic.isEnabled=true },2400) }
 private fun listen(){ if(busy)return; if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO),7);return}; try { val intent=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply { putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); putExtra(RecognizerIntent.EXTRA_LANGUAGE,"vi-VN"); putExtra(RecognizerIntent.EXTRA_PROMPT,"Hỏi Trùm AI đi nào!") }; startActivityForResult(intent,speechRequest) } catch(e:Exception){ Toast.makeText(this,"Điện thoại chưa có dịch vụ nhận dạng giọng nói",Toast.LENGTH_LONG).show() } }
 override fun onActivityResult(requestCode:Int,resultCode:Int,data:Intent?){ super.onActivityResult(requestCode,resultCode,data); if(requestCode==speechRequest && resultCode==RESULT_OK){ val words=data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); if(!words.isNullOrEmpty()){question.setText(words[0]); respond()} } }
 override fun onRequestPermissionsResult(requestCode:Int,permissions:Array<out String>,grantResults:IntArray){super.onRequestPermissionsResult(requestCode,permissions,grantResults);if(requestCode==7 && grantResults.firstOrNull()==PackageManager.PERMISSION_GRANTED) listen()}
 override fun onInit(status:Int){if(status==TextToSpeech.SUCCESS){ val vietnamese=Locale("vi","VN"); tts?.language=vietnamese; tts?.setSpeechRate(0.88f); tts?.setPitch(1.16f) }}
 override fun onDestroy(){handler.removeCallbacksAndMessages(null);tts?.stop();tts?.shutdown();super.onDestroy()}
}

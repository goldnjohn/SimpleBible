package goldnjohn.simplebible

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.lang.Exception
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity (), AdapterView.OnItemSelectedListener {

    private lateinit var t2: TextView
    private lateinit var t1: TextView
    private var book : String = "Genesis"
    private var version : String = "english_nasb.xml"
    private lateinit var elementBook : Element
    private lateinit var elementChapter : Element
    private var chapter : Int = 1
    private  var s = arrayOfNulls<String>(200)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        t1 = findViewById(R.id.heading)
        t2 = findViewById(R.id.textView)
        //Toast.makeText(applicationContext)
        val sp1 : Spinner = findViewById(R.id.spinner1)
        ArrayAdapter.createFromResource(
                this,
                R.array.book,
                android.R.layout.simple_spinner_item
        ).also { book ->
            book.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp1.adapter = book
            sp1.onItemSelectedListener = this
        }
        val sp2 : Spinner = findViewById(R.id.spinner2)
        ArrayAdapter.createFromResource(
                this,
                R.array.chapter,
                android.R.layout.simple_spinner_item
        ).also { chapter ->
            chapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp2.adapter = chapter
            sp2.onItemSelectedListener = this
        }
        val sp3 : Spinner = findViewById(R.id.spinner3)
        ArrayAdapter.createFromResource(
                this,
                R.array.version,
                android.R.layout.simple_spinner_item
        ).also { version ->
            version.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp3.adapter = version
            sp3.onItemSelectedListener = this
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val ch : String = parent?.getItemAtPosition(position).toString()
        if(ch.matches("\\d+".toRegex()))
            chapter = ch.toInt()
        else if (ch == "Telugu-BSI")
            version = "telugu_bsi.xml"
        else if (ch == "English-NASB")
            version = "english_nasb.xml"
        else
            book = ch
        try {
            parseBible()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun parseBible() {
        val doc : Document= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(assets.open(version))
        doc.documentElement.normalize()
        val nList : NodeList= doc.getElementsByTagName("BIBLEBOOK")
        for (i in 0 until nList.length){
            val node : Node = nList.item(i)
            if(node.nodeType == Node.ELEMENT_NODE){
                elementBook = node as Element
                val mList = elementBook.childNodes
                for (j in 0 until mList.length){
                    val node1 = mList.item(j)
                    if(node1.nodeType == Node.ELEMENT_NODE){
                        elementChapter = node1 as Element
                        printText(elementBook,elementChapter,book,chapter)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun printText(elementBook: Element, elementChapter: Element, b: String, c: Int) {
        t2.text = ""
        if(elementBook.getAttribute("bname") == b){
            if(elementChapter.getAttribute("cnumber") == c.toString()){
                t1.text = b +" "+ c + "\n"
                for(k in 0..100){
                    val z = k+1
                    s[k] = elementChapter.getElementsByTagName("VERS").item(k).textContent
                    t2.text =t2.text.toString() + "$z" +" "+ s[k] + "\n\n"
                }
            }
        }
    }
}

package com.tritongames.shopwishlist

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

@SuppressLint("StaticFieldLeak")
object JSONclasses {
    var e_mail = ""
    var name = ""
    var wish1 = ""
    var wish2 = ""
    var wish3 = ""
    var urlpic = ""
    var wishes = arrayOfNulls<String>(30)
    var email = arrayOfNulls<String>(10)
    var names = arrayOfNulls<String>(10)
    var urlPic = arrayOfNulls<String>(10)
    var dataJSON = ""
    var context: Context? = null
    var name_pick = ""

    /*suspend fun GetJSON(urlString: String) {
        LifecycleCoroutineScope.launch {
            val jSONString: String = readJSON(urlString)
            executeReadJSON(jSONString)

        }

    }*/

    fun readJSON(urlString: String): String {

        val client: OkHttpClient = OkHttpClient()

        val request: Request = Request.Builder().url(urlString).build()
        val response: Unit = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                dataJSON = response.body.toString()
            }
        })

        return dataJSON
    }


    private fun executeReadJSON(result: String?) {

// retrieving full name from EditText and collapsing into one long string
        val etName: String = Shop.Companion.NAME?.text.toString()
        val spaceInt = etName.indexOf(" ")
        val firstNameID = etName.substring(0, spaceInt)
        val lastNameID = etName.substring(spaceInt + 1, etName.length)
        val fullNameID: String = firstNameID.plus(lastNameID)

// passing on both Json string result and fullNameID to locate array
        val jObj: JSONObject
        try {
            jObj = JSONObject(result)
            val jArr: JSONArray = jObj.getJSONArray(fullNameID)
            Toast.makeText(context, "Searching for $etName", Toast.LENGTH_SHORT).show()
            if (jArr.toString().equals(fullNameID)) {
                Toast.makeText(context, "$etName has been found.", Toast.LENGTH_SHORT).show()
                for (j in 0 until jArr.length()) {
                    val kObj: JSONObject = jArr.getJSONObject(j)
                    e_mail = kObj.getString("email")
                    email[j] = e_mail
                    name = kObj.getString("name")
                    /*if (name.matches(firstNameID)) {
                        val greeting : String = "Welcome $name"
                        Shop.Companion.NAME?.setText(greeting)
                    }*/
                    names[j] = name
                    wish1 = kObj.getString("wish1")
                    wishes[j] = wish1
                    wish2 = kObj.getString("wish2")
                    wishes[j + 1] = wish2
                    wish3 = kObj.getString("wish3")
                    wishes[j + 2] = wish3
                    urlpic = kObj.getString("urlPic")
                    urlPic[j] = urlpic
                }
            } else {
                Toast.makeText(
                    context,
                    "$etName not found. Creating new list...",
                    Toast.LENGTH_SHORT
                ).show()
                //val writeJson = writeJSON()
                //writeJson.execute("http://www.mediaandroid.byethost17.com/wishjson.json", dataJSON)
            }
        } catch (e: JSONException) {
// TODO Auto-generated catch block
            e.printStackTrace()
        }
    }


}




/*private fun writeJSON : {
    var `in`: InputStream? = null
    var json = ""
    var result = ""

    @Override
    protected fun doInBackground(vararg params: String?): String {
        val client: HttpClient = DefaultHttpClient()
        try {
            val uriOutFile: URI = URI.create(params[0])
            val hPost = HttpPost(uriOutFile)
            val jo = JSONObject(params[1])
            val jArr = JSONArray()
            jArr.put(jo.accumulate("name", ""))
            jArr.put(jo.accumulate("DOB", ""))
            jArr.put(jo.accumulate("urlPic", ""))
            jArr.put(jo.accumulate("email", ""))
            jArr.put(jo.accumulate("wish1", ""))
            jArr.put(jo.accumulate("wish2", ""))
            jArr.put(jo.accumulate("wish3", ""))
            json = jArr.toString()
            val se = StringEntity(json)
            hPost.setEntity(se)
            hPost.setHeader("Accept", "application/json")
            hPost.setHeader("Content-type", "application/json")
            val hResponse: HttpResponse = client.execute(hPost)
            `in` = hResponse.getEntity().getContent()
            val bufferedReader = BufferedReader(InputStreamReader(`in`))
            var line = ""
            while (bufferedReader.readLine().also { line = it } != null) result += line
            `in`!!.close()
        } catch (e: ClientProtocolException) {
// TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
// TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: JSONException) {
// TODO Auto-generated catch block
            e.printStackTrace()
        }
        return result
    }

    @Override
    protected fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }*/

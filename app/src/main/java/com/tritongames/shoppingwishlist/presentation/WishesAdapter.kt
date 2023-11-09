
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tritongames.shoppingwishlist.R
import com.tritongames.shoppingwishlist.data.models.catalog.CatalogProducts

class WishesAdapter: RecyclerView.Adapter<WishesAdapter.WishesViewHolder>(){
    class WishesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object: DiffUtil.ItemCallback<CatalogProducts>(){
        override fun areItemsTheSame(oldItem: CatalogProducts, newItem: CatalogProducts): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatalogProducts, newItem: CatalogProducts): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var wishesList: List<CatalogProducts>
        get() = differ.currentList
        set(value) {differ.submitList(value)}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishesViewHolder {
        return WishesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.wishes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WishesViewHolder, position: Int) {

            val wish = wishesList[position]



    }

    override fun getItemCount(): Int {
        return wishesList.size
    }
}
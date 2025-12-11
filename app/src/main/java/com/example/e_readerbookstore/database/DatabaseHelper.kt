package com.example.e_readerbookstore.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.e_readerbookstore.R
import com.example.e_readerbookstore.model.Book
import com.example.e_readerbookstore.model.CartItem
import com.example.e_readerbookstore.model.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "EReader.db"
        private const val DATABASE_VERSION = 2 // Incremented version

        // Users Table
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"

        // Books Table
        const val TABLE_BOOKS = "books"
        const val COLUMN_BOOK_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_IMAGE_RES_ID = "image_res_id"
        const val COLUMN_IMAGE_URL = "image_url" // New Column
        const val COLUMN_IS_IN_STOCK = "is_in_stock"
        const val COLUMN_PRICE = "price"
        const val COLUMN_CATEGORY = "category"

        // Cart/Favorites Table
        const val TABLE_CART_FAVORITES = "cart_favorites"
        const val COLUMN_CF_ID = "id"
        const val COLUMN_CF_USER_ID = "user_id"
        const val COLUMN_CF_BOOK_ID = "book_id"
        const val COLUMN_CF_TYPE = "type" // 0 for Cart, 1 for Favorite
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = ("CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT" + ")")
        db.execSQL(createUsersTable)

        val createBooksTable = ("CREATE TABLE " + TABLE_BOOKS + "("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_IMAGE_RES_ID + " INTEGER,"
                + COLUMN_IMAGE_URL + " TEXT,"
                + COLUMN_IS_IN_STOCK + " INTEGER," // 0 or 1
                + COLUMN_PRICE + " REAL,"
                + COLUMN_CATEGORY + " TEXT" + ")")
        db.execSQL(createBooksTable)

        val createCartFavoritesTable = ("CREATE TABLE " + TABLE_CART_FAVORITES + "("
                + COLUMN_CF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CF_USER_ID + " INTEGER,"
                + COLUMN_CF_BOOK_ID + " INTEGER,"
                + COLUMN_CF_TYPE + " INTEGER" + ")")
        db.execSQL(createCartFavoritesTable)

        seedBooks(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CART_FAVORITES")
        onCreate(db)
    }

    private fun seedBooks(db: SQLiteDatabase) {
        val books = listOf(
            // Self Help
            Book(title = "How to Win Friends and Influence People", author = "Dale Carnegie", imageUrl = "https://covers.openlibrary.org/b/isbn/9780671027032-L.jpg", isInStock = true, price = 10.0, category = "Self Help"),
            Book(title = "Atomic Habits", author = "James Clear", imageUrl = "https://covers.openlibrary.org/b/isbn/9780735211292-L.jpg", isInStock = true, price = 10.0, category = "Self Help"),
            Book(title = "The 7 Habits of Highly Effective People", author = "Stephen Covey", imageUrl = "https://covers.openlibrary.org/b/isbn/9780743269513-L.jpg", isInStock = true, price = 10.0, category = "Self Help"),
            Book(title = "The 48 Laws of Power", author = "Robert Greene", imageUrl = "https://covers.openlibrary.org/b/isbn/9780140280197-L.jpg", isInStock = true, price = 20.0, category = "Self Help"),
            Book(title = "The Subtle Art of Not Giving a F*ck", author = "Mark Manson", imageUrl = "https://covers.openlibrary.org/b/isbn/9780062457714-L.jpg", isInStock = true, price = 18.0, category = "Self Help"),
            Book(title = "The Art of War", author = "Sun Tzu", imageUrl = "https://covers.openlibrary.org/b/isbn/9781590302255-L.jpg", isInStock = true, price = 11.0, category = "Self Help"),
            Book(title = "Rich Dad Poor Dad", author = "Robert Kiyosaki", imageUrl = "https://covers.openlibrary.org/b/isbn/9781612680194-L.jpg", isInStock = false, price = 0.0, category = "Self Help"), // Out of stock
            Book(title = "The Art of Seduction", author = "Robert Greene", imageUrl = "https://covers.openlibrary.org/b/isbn/9780142001196-L.jpg", isInStock = true, price = 12.0, category = "Self Help"),

            // Fiction
            Book(title = "1984", author = "George Orwell", imageUrl = "https://covers.openlibrary.org/b/isbn/9780451524935-L.jpg", isInStock = true, price = 15.0, category = "Fiction"),
            Book(title = "The Great Gatsby", author = "F. Scott Fitzgerald", imageUrl = "https://covers.openlibrary.org/b/isbn/9780743273565-L.jpg", isInStock = true, price = 12.0, category = "Fiction"),
            Book(title = "To Kill a Mockingbird", author = "Harper Lee", imageUrl = "https://covers.openlibrary.org/b/isbn/9780061120084-L.jpg", isInStock = true, price = 14.0, category = "Fiction"),
            Book(title = "Pride and Prejudice", author = "Jane Austen", imageUrl = "https://covers.openlibrary.org/b/isbn/9780141439518-L.jpg", isInStock = true, price = 13.0, category = "Fiction"),
            Book(title = "The Catcher in the Rye", author = "J.D. Salinger", imageUrl = "https://covers.openlibrary.org/b/isbn/9780316769174-L.jpg", isInStock = true, price = 11.0, category = "Fiction"),
            Book(title = "Lord of the Flies", author = "William Golding", imageUrl = "https://covers.openlibrary.org/b/isbn/9780571056866-L.jpg", isInStock = true, price = 10.0, category = "Fiction"),

            // Scientific
            Book(title = "A Brief History of Time", author = "Stephen Hawking", imageUrl = "https://covers.openlibrary.org/b/isbn/9780553380163-L.jpg", isInStock = true, price = 16.0, category = "Scientific"),
            Book(title = "The Selfish Gene", author = "Richard Dawkins", imageUrl = "https://covers.openlibrary.org/b/isbn/9780192860927-L.jpg", isInStock = true, price = 17.0, category = "Scientific"),
            Book(title = "Cosmos", author = "Carl Sagan", imageUrl = "https://covers.openlibrary.org/b/isbn/9780345331359-L.jpg", isInStock = true, price = 15.0, category = "Scientific"),
            Book(title = "The Origin of Species", author = "Charles Darwin", imageUrl = "https://covers.openlibrary.org/b/isbn/9780451529060-L.jpg", isInStock = true, price = 18.0, category = "Scientific"),
            Book(title = "Sapiens", author = "Yuval Noah Harari", imageUrl = "https://covers.openlibrary.org/b/isbn/9780062316097-L.jpg", isInStock = true, price = 19.0, category = "Scientific"),

            // History
            Book(title = "Guns, Germs, and Steel", author = "Jared Diamond", imageUrl = "https://covers.openlibrary.org/b/isbn/9780393317558-L.jpg", isInStock = true, price = 20.0, category = "History"),
            Book(title = "The Rise and Fall of the Third Reich", author = "William L. Shirer", imageUrl = "https://covers.openlibrary.org/b/isbn/9780671624202-L.jpg", isInStock = true, price = 25.0, category = "History"),
            Book(title = "A People's History of the United States", author = "Howard Zinn", imageUrl = "https://covers.openlibrary.org/b/isbn/9780060838652-L.jpg", isInStock = true, price = 22.0, category = "History"),
            Book(title = "The History of the Ancient World", author = "Susan Wise Bauer", imageUrl = "https://covers.openlibrary.org/b/isbn/9780393059748-L.jpg", isInStock = true, price = 21.0, category = "History"),
            Book(title = "The Silk Roads", author = "Peter Frankopan", imageUrl = "https://covers.openlibrary.org/b/isbn/9781101912379-L.jpg", isInStock = true, price = 23.0, category = "History")
        )

        for (book in books) {
            val values = ContentValues().apply {
                put(COLUMN_TITLE, book.title)
                put(COLUMN_AUTHOR, book.author)
                put(COLUMN_IMAGE_RES_ID, R.drawable.app_logo) // Default placeholder
                put(COLUMN_IMAGE_URL, book.imageUrl)
                put(COLUMN_IS_IN_STOCK, if (book.isInStock) 1 else 0)
                put(COLUMN_PRICE, book.price)
                put(COLUMN_CATEGORY, book.category)
            }
            db.insert(TABLE_BOOKS, null, values)
        }
    }

    // User Operations
    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD, user.password)
        }
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun checkUser(username: String, password: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_PASSWORD),
            "$COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?",
            arrayOf(username, password),
            null, null, null
        )

        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            )
        }
        cursor.close()
        db.close()
        return user
    }

    // Book Operations
    fun getAllBooks(): List<Book> {
        return getBooksByCategory(null)
    }
    
    fun getBooksByCategory(category: String?): List<Book> {
        val books = ArrayList<Book>()
        val db = this.readableDatabase
        val query = if (category == null || category == "All") {
            "SELECT * FROM $TABLE_BOOKS"
        } else {
            "SELECT * FROM $TABLE_BOOKS WHERE $COLUMN_CATEGORY = ?"
        }
        val cursor = if (category == null || category == "All") {
            db.rawQuery(query, null)
        } else {
            db.rawQuery(query, arrayOf(category))
        }

        if (cursor.moveToFirst()) {
            do {
                val book = Book(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                    imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID)),
                    imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)) ?: "",
                    isInStock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_IN_STOCK)) == 1,
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                )
                books.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return books
    }

    // Cart/Favorite Operations
    fun addToCartOrFavorite(userId: Int, bookId: Int, type: Int): Long {
        val db = this.writableDatabase
        // Check if already exists
        val cursor = db.query(
            TABLE_CART_FAVORITES,
            arrayOf(COLUMN_CF_ID),
            "$COLUMN_CF_USER_ID=? AND $COLUMN_CF_BOOK_ID=? AND $COLUMN_CF_TYPE=?",
            arrayOf(userId.toString(), bookId.toString(), type.toString()),
            null, null, null
        )
        if (cursor.count > 0) {
            cursor.close()
            db.close()
            return -1 // Already exists
        }
        cursor.close()

        val values = ContentValues().apply {
            put(COLUMN_CF_USER_ID, userId)
            put(COLUMN_CF_BOOK_ID, bookId)
            put(COLUMN_CF_TYPE, type)
        }
        val id = db.insert(TABLE_CART_FAVORITES, null, values)
        db.close()
        return id
    }

    fun getItems(userId: Int, type: Int): List<Book> {
        val books = ArrayList<Book>()
        val db = this.readableDatabase
        val query = "SELECT b.* FROM $TABLE_BOOKS b INNER JOIN $TABLE_CART_FAVORITES cf ON b.$COLUMN_BOOK_ID = cf.$COLUMN_CF_BOOK_ID WHERE cf.$COLUMN_CF_USER_ID = ? AND cf.$COLUMN_CF_TYPE = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString(), type.toString()))

        if (cursor.moveToFirst()) {
            do {
                val book = Book(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                    imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID)),
                    imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)) ?: "",
                    isInStock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_IN_STOCK)) == 1,
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                )
                books.add(book)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return books
    }
    
    fun removeItem(userId: Int, bookId: Int, type: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_CART_FAVORITES, "$COLUMN_CF_USER_ID=? AND $COLUMN_CF_BOOK_ID=? AND $COLUMN_CF_TYPE=?", arrayOf(userId.toString(), bookId.toString(), type.toString()))
        db.close()
    }
}

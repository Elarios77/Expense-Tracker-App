# 💰 ExpenseWise: Smart Expense Tracker (Clean Architecture)

A comprehensive Android application developed with **Jetpack Compose**, designed to demonstrate the implementation of modern architecture (**Clean Architecture** / **MVVM**) and real-time data management.

The application serves as a personal expense tracking tool, offering dynamic data views and robust offline storage.

---

## 📱 Screenshots

Here are visual representations of the application's key screens.

| Home (Menu) | Expense List (History Tab) | Add Expense (Main Tab) | Statistics (Pie Chart) |
|:-----------:|:-----------------------------:|:----------------------------:|:------------------------:|
| 
<img src="path/to/main.jpg" width="250"/>
 | 
<img src="path/to/history.jpg" width="250"/>
 |
<img src="path/to/add_expense.jpg" width="250"/> 
 |
<img src="path/to/pie_chart.jpg" width="250"/>
 |

---

## 🏗️ Architecture & Technologies

### Key Components:

* **Architecture:** **Clean Architecture** (Enforcing separation of concerns and clear dependency rules).
* **Design Pattern:** **MVVM** (Model-View-ViewModel) for effective State Management.
* **Database:** **Room DB** (For local, offline data persistence).
* **Dependency Injection:** **Hilt** (For centralized management of all dependencies).
* **UI Framework:** [Jetpack Compose](Declarative UI, Material 3).
* **Asynchronous Flow:** [Kotlin Flow & Coroutines](For real-time data streaming and background operations).

### 🔑 The Key: Dynamic Navigation & Reusability

The project demonstrates the **DRY (Don't Repeat Yourself)** principle by:

* **Screens:** Instead of creating 6 separate screen files for each category (Food, Home, Transport, etc.), **ONLY one reusable screen** (`ExpenseListScreen`) is used.
* **Data Flow:** The **ViewModel** receives the category title (e.g., `"food"`) as an argument from the Navigation Graph (`details/{category}`), and uses the `GetExpensesByCategoryUseCase` to dynamically filter the data from the database.

---

---

## ✨ Application Functionality

* **State Management:** Utilization of **Sealed Classes** to manage UI state (Loading / Success / Error).
* **Dynamic Views:** The application features Tabs (Main/History) and displays the **Add Form** or **History List** system dynamically based on the user's selection.
* **Total & Analytics:** A Statistics Screen featuring a **Pie Chart** (MPAndroidChart Interoperability) that visualizes the distribution of expenses by category.
* **CRUD Operations:** Full functionality for Creating, Reading, and Deleting expenses (Delete/Remove).
* **Validation:** Basic input validation (e.g., positive amount, non-empty description).

---

## ⚙️ Setup and Installation

Follow these steps to get the project running on your local machine.

### Prerequisites

* **Android Studio:** Latest stable version (Bumblebee or newer).
* **Java Development Kit (JDK):** Version 11 or higher.
* An **Android Device** or **Emulator** running API level 21+.

### Installation Steps

1.  **Clone the Repository:**
    Open your terminal or command prompt and clone the project:
    ```bash
    git clone [https://github.com/Elarios77/Expense-Tracker-App.git]
    ```

2.  **Open in Android Studio:**
    Launch Android Studio and select **File > Open**, then navigate to the cloned `expensewise` directory and open the project.

3.  **Sync Project:**
    Android Studio will automatically prompt you to sync the project with the necessary Gradle dependencies. If it doesn't, click **File > Sync Project with Gradle Files**.

4.  **Build and Run:**
    * Select your target device or emulator from the dropdown menu in the toolbar.
    * Click the **Run** (▶️) button to build and deploy the application.



## 🧑‍💻 Author

Developed by **[Your Name]**

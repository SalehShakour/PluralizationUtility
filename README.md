# PluralizationUtility

PluralizationUtility is a Java library for pluralizing English nouns. It handles both regular and irregular plurals, as well as words that cannot be pluralized. While it strives for high accuracy, **please note that it may not produce the correct output for all words** due to the complexities and nuances of the English language.


## Features

* **Handles Irregular Plurals:** Accurately transforms nouns with irregular plural forms (e.g., "mouse" to "mice," "child" to "children").
* **Identifies Unpluralizable Words:** Recognizes words that do not have plural forms (e.g., "information," "advice").
* **Applies Standard Rules:** Implements standard pluralization rules for regular nouns (e.g., adding "-s" or "-es").
* **In-Memory Caching:** Stores previously calculated plurals for improved performance.
  
**Important:** Input words should be in their singular form.

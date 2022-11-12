<p align="center">
  <img src="https://user-images.githubusercontent.com/36176569/201490030-1abddcf8-a929-409c-b28f-b480a3a06bd0.gif" />
</p>

Simple project focused on learning a little more about how Telegram bots work and are created.


The bot has a very basic functionality: the user types a question related to programming and the bot responds with a list of results obtained from Google with a filter for Stack Overfow topics. The results are obtained through [web scraping](https://en.m.wikipedia.org/wiki/Web_scraping) using the library [Jsoup](https://jsoup.org/).

The project uses the [TelegramBotAPI](https://github.com/InsanusMokrassar/TelegramBotAPI) library to facilitate communication with the [Telegram Bot API](https://core.telegram.org/bots/api).

**Note:** This bot is not hosted on any server, so there is no username available so you can test and see it working in practice. Sorry.

## How to run and test

- First, clone this project.

#### On Telegram

- Connected to your Telegram account, start a conversation with ***@BotFather*** and create a new bot with whatever name you want.
- After creating your bot, copy its token to be used later in the code.
- (Optional) Add a /help command to your bot via @BotFather settings. This project can handle this command and send a help message, but if it doesn't add the command, it doesn't imply the bot's functionality.

#### In your IDE

- With your bot created and the project code updated with your token, run the project in your IDE of choice. You need to keep it running.
- Finally, start a conversation with your bot on Telegram. When starting the conversation, the bot should respond with a welcome message, indicating that it reacted to the /start command and everything is working. Submit questions and see if he answers them.

**Note:** In order for it to respond in real time, the project must be running in the IDE when you send the messages. If you send messages and the project is not running in the IDE, the bot will do nothing as it is not online and no server has been defined to handle messages at all times.

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "6307169775:AAGqacu4nuQPM8G1vWHF3hyLdCskugRe8N8";

    public static final String BOT_USERNAME = "t.me/ProjectNasaBot";

    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=hmfXdzTeW4270S6wK7mfKCkH6JdOAZn5mIhDYD4h";

    public static long chat_id = 000000000; //hiding Telegram chat ID

    public TelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chat_id = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Добрый день! Это бот, по запросу высылающий изображения с сайта NASA. " +
                            "Изображения обновляются каждый день");
                    break;
                case "/give":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/start":
                    sendMessage("Бот уже запущен");
                    break;
                default:
                    sendMessage("Неизвестная команда");
            }
        }
    }


    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

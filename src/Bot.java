import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;

/**
 * Created by LION on 9/25/2017.
 */
public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){

            String messageText =update.getMessage().getText();
            long chatID=update.getMessage().getChatId();

            SendMessage message =new SendMessage().setChatId(chatID).setText("hi "+messageText);

            try{
                sendMessage(message);
            }catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }else if(update.hasMessage() && update.getMessage().hasPhoto()){
            long chatID=update.getMessage().getChatId();

            List<PhotoSize> photoSizes=update.getMessage().getPhoto();

            String picID =photoSizes.stream().sorted(Comparator.comparing(PhotoSize :: getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();

            int picHeight =photoSizes.stream().sorted(Comparator.comparing(PhotoSize :: getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();

            int picwidth =photoSizes.stream().sorted(Comparator.comparing(PhotoSize :: getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();

            String caption ="width= "+Integer.toString(picwidth)+"\nheight= "+Integer.toString(picHeight);
            SendPhoto ms =new SendPhoto().setChatId(chatID).setPhoto(picID).setCaption(caption);

            try{
                sendPhoto(ms);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "OurFirstBot";
    }

    @Override
    public String getBotToken() {
        return "425601371:AAGnTvNQJTwH3dke5nt4HXn889VGacY6DnY";
    }
}

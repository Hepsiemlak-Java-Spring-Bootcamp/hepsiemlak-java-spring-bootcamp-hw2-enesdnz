package com.company.enesdeniz;

import com.company.enesdeniz.Factory.RealEstateFactory;
import com.company.enesdeniz.Factory.UserFactory;
import com.company.enesdeniz.enums.UserType;
import com.company.enesdeniz.model.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        //Estates with factory pattern
        RealEstateFactory estateFactory = new RealEstateFactory();

        RealEstate Estate1 = estateFactory.createRealEstate("İstanbul", "Kadıköy", "3+1");
        RealEstate Estate2 = estateFactory.createRealEstate("İzmir", "Bostanlı", "3+1");
        RealEstate Estate3 = estateFactory.createRealEstate("İstanbul", "Beşiktaş", "2+1");
        RealEstate Estate4 = estateFactory.createRealEstate("Ankara", "Çankaya", "1+1");

        //Users with factory pattern
        UserFactory userfactory = new UserFactory();

        User user1 = userfactory.createUser(UserType.Bireysel.toString(), "Enes Deniz", "enesdeniz@gmail.com");
        user1.setMessageBox(prepareMessageBox());

        User user2 = userfactory.createUser(UserType.Bireysel.toString(), "Selen Toraman", "selentoraman@gmail.com");
        user2.setMessageBox(prepareMessageBox());

        User user3 = userfactory.createUser(UserType.Kurumsal.toString(), "Hatice Emlak", "haticedeniz@gmail.com");
        user3.setMessageBox(prepareMessageBox());

        //allUserList
        List<User> allUsersList = new ArrayList<>();
        allUsersList.add(user1);
        allUsersList.add(user2);
        allUsersList.add(user3);

        //Sistemdeki tüm mesajları ve bu mesajları yazan kullanıcıları ekrana yazdıran fonksiyon
        printAllMessagesByUsers(allUsersList);

        List<Advert> advertList = new ArrayList<>();
        advertList.add(prepareAdvert("Kadıkoy Moda Sahilde Denize Sifir", user1, Estate1, BigDecimal.valueOf(2500)));
        advertList.add(prepareAdvert("Dosta GİDERRR ACİLLL!!!", user2, Estate2, BigDecimal.valueOf(3000)));
        advertList.add(prepareAdvert("Öğrenciye ve Bekar uygun", user3, Estate3, BigDecimal.valueOf(1700)));
        advertList.add(prepareAdvert("Uygun ve ucuz keçiören", user3, Estate4, BigDecimal.valueOf(1500)));

        List<String> roomNumbers = new ArrayList<String>();
        roomNumbers.add("3+1");
        roomNumbers.add("2+1");

        // İstanbuldaki 3+1 ve 2+1, 3000 liranın altındaki ilanlar
        List<Advert> advertsIstanbul = getAdvertsByRoomNumber(roomNumbers,getAdvertsByProvince("İstanbul",getAdvertsByHighestPrice(BigDecimal.valueOf(3000), advertList)));

        user1.setFavoritePosts(makeFavoritePosts(advertsIstanbul));
        // verilen emaile sahip kullanıcının tüm favori postlarını döndüren method
        List<Advert> favoriteAdverts = getFavouritePostsByUserEmail(user1.getEmail(),allUsersList);

        System.out.println("\n-------Enes isimli kullanıcının favori ilanları-------\n");
        for (Advert advert : favoriteAdverts) {
            System.out.println(advert.getTitle() + " " + advert.getRealEstate().getProvince() + " " + advert.getRealEstate().getDistrict());
        }

    }
    //method that prepares favorite posts
    private static Set<Advert> makeFavoritePosts(List<Advert> favoriteAdverts) {
        Set<Advert> favoritePosts = new HashSet<>();
        for(Advert advert : favoriteAdverts)
        {
            favoritePosts.add(advert);
        }
        return favoritePosts;
    }

    //method that prepares message box
    private static List<Message> prepareMessageBox() {
        List<Message> messageBox = new ArrayList<>();
        messageBox.add(new Message("Mesaj Başlığı 1"));
        messageBox.add(new Message("Mesaj Başlığı 2"));
        messageBox.add(new Message("Mesaj Başlığı 3"));
        return messageBox;
    }

    //method that prepares advertisements
    private static Advert prepareAdvert(String title, User user, RealEstate realEstate, BigDecimal price) {
        Advert advert = new Advert();

        advert.setTitle(title);

        advert.setRealEstate(makeRealEstate(realEstate));

        advert.setUser(user);

        advert.setActive(true);

        advert.setPhotoList(makePhotoList());

        advert.setPrice(price);

        return advert;
    }

    private static String[] makePhotoList() {
        String[] photoList = new String[5];
        photoList[0] = "https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg";
        photoList[1] = "https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg";
        return photoList;
    }

    private static RealEstate makeRealEstate(RealEstate estate) {
        return new RealEstate(estate.getProvince(), estate.getDistrict(), estate.getNumberOfRooms());
    }

    //method that returns postings by room number
    private static List<Advert> getAdvertsByRoomNumber(List<String> numberOfRooms, List<Advert> allAdverts)
    {
        List<Advert> advertListByRoomNumber = new ArrayList<>();
        for (Advert advert: allAdverts)
        {
            if(numberOfRooms.contains(advert.getRealEstate().getNumberOfRooms()))
                advertListByRoomNumber.add(advert);

        }
        return advertListByRoomNumber;
    }

    //returns classifieds by city
    private static List<Advert> getAdvertsByProvince(String provinceName, List<Advert> allAdverts)
    {
        List<Advert> advertListByProvince = new ArrayList<>();
        for (Advert advert: allAdverts)
        {
            if(advert.getRealEstate().getProvince().equals(provinceName))
                advertListByProvince.add(advert);
        }
        return advertListByProvince;
    }

    //returns the highest priced listings
    private static List<Advert> getAdvertsByHighestPrice(BigDecimal price, List<Advert> allAdverts)
    {
        List<Advert> advertListByHighestPrice = new ArrayList<>();
        for (Advert advert: allAdverts)
        {
            if(advert.getPrice().compareTo(price) == -1)
                advertListByHighestPrice.add(advert);
        }
        return advertListByHighestPrice;
    }

    //method that returns the user's favorite postings based on the given e-mail
    private static List<Advert> getFavouritePostsByUserEmail(String userEmail, List<User> allUsers)
    {
        List<Advert> advertFavouritePosts = new ArrayList<>();
        for (User user: allUsers)
        {
            if(user.getEmail().equals(userEmail))
            {
                advertFavouritePosts.addAll(user.getFavoritePosts());
            }
        }
        return advertFavouritePosts;
    }

    //This method that returns incoming messages to all users
    private static void printAllMessagesByUsers(List<User> allUsers)
    {
        System.out.println("\n-------Sistemdeki kayıtlı kullanıcılara gelen mesajlar-------\n");
        for (User user: allUsers)
        {
            for(Message msg : user.getMessageBox())
            {
                System.out.println(msg.getTitle() + " " + user.getName());
            }
        }
    }
}

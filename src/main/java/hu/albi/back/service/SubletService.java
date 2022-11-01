package hu.albi.back.service;

import hu.albi.back.model.FileInfo;
import hu.albi.back.model.Sublet;
import hu.albi.back.model.SubletInfo;
import hu.albi.back.model.User;
import hu.albi.back.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hu.albi.back.repo.SubletRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubletService {

    private static SubletRepository subletRepository;
    private static ImageRepository imageRepository;

    public SubletService(SubletRepository subletRepository, ImageRepository imageRepository){
        this.subletRepository = subletRepository;
        this.imageRepository = imageRepository;
    }

    @Autowired
    private UserService userService;

    public List<Sublet> getSublets() {
        return subletRepository.findAll();
    }

    public List<SubletInfo> getSubletInfosByUser(int userId) {
        List<Sublet> sublets = subletRepository.findSubletByUser(userId);
        return connectSubletInfos(sublets);
    }

    private List<SubletInfo> connectSubletInfos(List<Sublet> sublets){
        List<SubletInfo> subletinfos = new ArrayList<>();
        for(Sublet s : sublets){
            SubletInfo si = new SubletInfo();

            User user = userService.findUserById(Long.valueOf(s.getSellerId()));
            if(user == null){
                si.setEmail("hibás adat");
                si.setPhone("hibás adat");
            }else {
                si.setPhone(user.getTel());
                si.setEmail(user.getEmail());
            }

            List<FileInfo> imageinfos = imageRepository.findFileInfoBySubletId(s.getId());

            List<String> images = new ArrayList<>();

            for(FileInfo fi : imageinfos){
                images.add(fi.getUrl());
            }

            si.setId(s.getId());
            si.setAddress(s.getAddress());
            si.setSize(s.getSize());
            si.setDescript(s.getDesc());
            si.setRooms(s.getRooms());
            si.setPrice(s.getPrice());
            si.setGarden(s.getGarden());
            si.setImages(images);

            subletinfos.add(si);
        }
        return subletinfos;
    }

    public List<SubletInfo> getSubletInfos() {
        List<Sublet> sublets = subletRepository.findAll();
        return connectSubletInfos(sublets);
    }

    public List<SubletInfo> getSubletInfos(String command) {
        List<Sublet> sublets;
        switch (command) {
            case "priceAsc": sublets = subletRepository.orderSubletByPriceAsc(); break;
            case "priceDesc": sublets = subletRepository.orderSubletByPriceDesc(); break;
            case "sizeAsc": sublets = subletRepository.orderSubletBySizeAsc(); break;
            case "sizeDesc": sublets = subletRepository.orderSubletBySizeDesc(); break;
            case "onlyGarden": sublets = subletRepository.findSubletByGarden(); break;
            case "noGarden": sublets = subletRepository.findSubletByNoGarden(); break;

            default: sublets = subletRepository.findAll();
        }

        return connectSubletInfos(sublets);
    }

    public List<SubletInfo > findSubletInfosByAddress(String addr){
        List<Sublet> sublets = subletRepository.findSubletByAddress(addr);
        return connectSubletInfos(sublets);
    }


    public Sublet addSublet(Sublet sublet){
        return subletRepository.save(sublet);
    }

    public void addImagesToSublet(Integer id, String[] images){
        for (String url : images) {
            FileInfo fi = new FileInfo();
            fi.setSubletId(id);
            fi.setName(url);
            fi.setUrl("http://localhost:8080/files/" + url);
            imageRepository.save(fi);
        }

    }

    public Sublet findSubletById(Integer id){
        return subletRepository.findSubletById(id);
    }


    public static void deleteSubletByAdmin(Integer id){
        subletRepository.deleteSubletById(id);
    }

    public static void deleteSubletByUser(Integer id, Integer userId) throws Exception{
        Sublet result = subletRepository.findSubletById(id);
        if(result == null){
            throw new Exception("Hirdetés nem található!");
        }
        if(subletRepository.findSubletById(id).getSellerId().equals(userId)){
            subletRepository.deleteSubletById(id);
        }
        else{
            throw new Exception("Csak saját hirdetést lehet törölni!");
        }
    }

}

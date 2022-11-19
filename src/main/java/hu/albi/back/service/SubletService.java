package hu.albi.back.service;

import hu.albi.back.builder.SubletInfoBuilder;
import hu.albi.back.model.FileInfo;
import hu.albi.back.model.Sublet;
import hu.albi.back.model.SubletInfo;
import hu.albi.back.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import hu.albi.back.repo.SubletRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubletService {

    private static SubletRepository subletRepository;
    private static ImageRepository imageRepository;

    public SubletService(SubletRepository subletRepository, ImageRepository imageRepository) {
        SubletService.subletRepository = subletRepository;
        SubletService.imageRepository = imageRepository;
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

    private List<SubletInfo> connectSubletInfos(List<Sublet> sublets) {
        List<SubletInfo> subletinfos = new ArrayList<>();
        SubletInfoBuilder builder = new SubletInfoBuilder(imageRepository, userService);

        for (Sublet s : sublets) {
            SubletInfo si = builder.build(s);
            subletinfos.add(si);
        }
        return subletinfos;
    }

    public List<SubletInfo> getSubletInfos() {
        List<Sublet> sublets = subletRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return connectSubletInfos(sublets);
    }

    public List<SubletInfo> getSubletInfos(String order, String address) {
        List<Sublet> sublets;
        switch (order) {
            case "priceAsc":
                sublets = subletRepository.orderSubletByPriceAsc(address);
                break;
            case "priceDesc":
                sublets = subletRepository.orderSubletByPriceDesc(address);
                break;
            case "sizeAsc":
                sublets = subletRepository.orderSubletBySizeAsc(address);
                break;
            case "sizeDesc":
                sublets = subletRepository.orderSubletBySizeDesc(address);
                break;
            case "onlyGarden":
                sublets = subletRepository.findSubletByGarden(address);
                break;
            case "noGarden":
                sublets = subletRepository.findSubletByNoGarden(address);
                break;
            default:
                sublets = subletRepository.findSubletByAddress(address);
        }
        return connectSubletInfos(sublets);
    }

    public List<SubletInfo> findSubletInfosByAddress(String addr) {
        List<Sublet> sublets = subletRepository.findSubletByAddress(addr);
        return connectSubletInfos(sublets);
    }

    public Sublet addSublet(Sublet sublet) {
        return subletRepository.save(sublet);
    }

    public void addImagesToSublet(Integer id, String[] images) {
        for (String url : images) {
            FileInfo fi = new FileInfo();
            fi.setSubletId(id);
            fi.setName(url);
            fi.setUrl("http://localhost:8080/files/" + url);
            imageRepository.save(fi);
        }

    }

    public Sublet findSubletById(Integer id) {
        return subletRepository.findSubletById(id);
    }

    public static void deleteSublet(Integer id) {
        subletRepository.deleteSubletById(id);
    }

    public static void deleteSubletByUser(Integer id, Integer userId) throws Exception {
        Sublet result = subletRepository.findSubletById(id);
        if (result == null) {
            throw new Exception("Hirdetés nem található!");
        }
        if (subletRepository.findSubletById(id).getSellerId().equals(userId)) {
            deleteSublet(id);
        } else {
            throw new Exception("Csak saját hirdetést lehet törölni!");
        }
    }
}

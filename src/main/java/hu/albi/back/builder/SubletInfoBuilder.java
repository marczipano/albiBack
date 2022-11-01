package hu.albi.back.builder;

import hu.albi.back.model.FileInfo;
import hu.albi.back.model.Sublet;
import hu.albi.back.model.SubletInfo;
import hu.albi.back.model.User;
import hu.albi.back.repo.ImageRepository;
import hu.albi.back.repo.SubletRepository;
import hu.albi.back.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class SubletInfoBuilder {

    private static SubletRepository subletRepository;
    private static ImageRepository imageRepository;
    private static UserService userService;

    public SubletInfoBuilder(SubletRepository subletRepository, ImageRepository imageRepository, UserService userService){
        SubletInfoBuilder.subletRepository = subletRepository;
        SubletInfoBuilder.imageRepository = imageRepository;
        SubletInfoBuilder.userService = userService;
    }

    public SubletInfo build(Sublet s){
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

        return si;
    }
}

package controller;

import model.user.Auction;
import service.IGenericService;
import service.userService.AuctionService;

import java.util.List;

public class AuctionController implements IGenericService<Auction,Integer> {
    AuctionService auctionService = new AuctionService();
    @Override
    public List<Auction> findAll() {
        return auctionService.findAll();
    }

    @Override
    public void save(Auction auction) {
        auctionService.save(auction);
    }

    @Override
    public Auction findById(Integer id) {
        return auctionService.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
    }
    public int newId(){
        return auctionService.newID();
    }
}

package service.userService;

import config.Config;
import model.user.Auction;
import service.IGenericService;

import java.util.List;

public class AuctionService implements IGenericService<Auction,Integer> {
    List<Auction> auctions = new config.Config<Auction>().readFromFile(config.Config.PATH_AUCTION);
    @Override
    public List<Auction> findAll() {
        return auctions;
    }

    @Override
    public void save(Auction auction) {
        if (findById(auction.getId()) == null) {
            // add
            auctions.add(auction);
        } else {
            // update
            auctions.set(auctions.indexOf(findById(auction.getId())), auction);
        }
        new Config<Auction>().writeFromFile(Config.PATH_AUCTION, auctions);
    }

    @Override
    public Auction findById(Integer id) {
        for (int i = 0; i < auctions.size(); i++) {
            if (auctions.get(i).getId() == id) {
                return auctions.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    public int newID() {
        int max = 0;
        for (Auction auction : auctions) {
            if (auction.getId() > max)
                max = auction.getId();
        }
        return max + 1;
    }
}

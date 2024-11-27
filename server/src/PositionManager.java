import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PositionManager {
    private final ArrayList<CarInfo> carPositions; // Stores the bounding boxes of cars
    private final ArrayList<IntersectionInfo> intersectionsInfo; // Stores the information of intersection
    private final ArrayList<PeopleInfo> peopleInfos; // Stores the positions of people
    private final Lock lock = new ReentrantLock(); // Ensures thread-safe operations

    public PositionManager() {
        carPositions = new ArrayList<>();
        intersectionsInfo = new ArrayList<>();
        peopleInfos = new ArrayList<>();
    }


    public int registerCar(int x, int y, int moveType) {
        lock.lock();
        try {
            int newId=carPositions.size();
            for( int i=0; i<newId; i++){
                if(!carPositions.get(i).isActive()){
                    newId=i;
                }
            }
            if(newId==carPositions.size()){
                if(isCollisionFree(-1,x,y,moveType)){
                    carPositions.add(newId,new CarInfo(x,y,moveType));
                    return carPositions.size()-1;
                }else{
                    return -1;
                }
            } else{
                if(isCollisionFree(-1,x,y,moveType)){
                    updateCar(newId,x,y,moveType);
                    carPositions.get(newId).setActive(true);
                    return newId;
                }else{
                    return -1;
                }
            }

        } finally {
            lock.unlock();
        }
    }

    public void updateCar(int id, int x, int y, int moveType) {
        lock.lock();
        try {
            carPositions.get(id).update(x,y,moveType);
        } finally {
            lock.unlock();
        }
    }


    public void deleteCar(int id) {
        lock.lock();
        try {
            carPositions.get(id).setActive(false);
            while (!carPositions.isEmpty()&&!carPositions.getLast().isActive()){
                carPositions.removeLast();
            }
        } finally {
            lock.unlock();
        }
    }


    public int registerIntersection(int x, int y, int moveType) {
        lock.lock();
        try {
            intersectionsInfo.add(new IntersectionInfo(x,y,moveType));
            return intersectionsInfo.size()-1;
        } finally {
            lock.unlock();
        }
    }

    public void updateIntersection(int id, int x, int y, int moveType) {
        lock.lock();
        try {
            intersectionsInfo.get(id).update(x,y,moveType);
        } finally {
            lock.unlock();
        }
    }


    public int registerPerson(int x, int y) {
        lock.lock();
        try {
            peopleInfos.add(new PeopleInfo(x,y));
        } finally {
            lock.unlock();
            return peopleInfos.size()-1;
        }
    }

    public void updatePeople(int id, int x, int y) {
        lock.lock();
        try {
            peopleInfos.get(id).update(x,y);
        } finally {
            lock.unlock();
        }
    }

    public void deletePeople(int id) {
        lock.lock();
        try {
            peopleInfos.removeLast();
        } finally {
            lock.unlock();
        }
    }

    public boolean isGreen(int id, int x, int y, int movtype) {
        lock.lock();
        Rectangle bounds = (new CarInfo(x,y,movtype)).getBounds();
        try {
            Rectangle rect=null;
            for (int i=0; i< intersectionsInfo.size(); i++) {
                rect = intersectionsInfo.get(i).getBounds();
                if (rect.intersects(bounds)) {
                    boolean ans =intersectionsInfo.get(i).check(bounds);
                    return ans;
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean isCollisionFree(int id, int x, int y, int movtype) {
        Rectangle bounds = (new CarInfo(x,y,movtype)).getBigBounds();
        lock.lock();
        try {

            for (int i=0; i< peopleInfos.size(); i++) {
                Rectangle rect = peopleInfos.get(i).getBounds();
                if (rect.intersects(bounds)) {
                    return false;
                }
            }
            for (int i=0; i< carPositions.size(); i++) {
                if(i==id||!carPositions.get(i).isActive())
                    continue;
                CarInfo car=carPositions.get(i);
                Rectangle rect = car.getBounds();
                if (rect.intersects(bounds)) {
                    return false;
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }
}

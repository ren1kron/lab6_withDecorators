package server.managers;

import general.models.Worker;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Manages collection
 * @author ren1kron
 */

public class CollectionManager implements Iterable<Worker> {
    private int currentId = 1;
    private Map<Integer, Worker> keyMap = new LinkedHashMap<>();
//    private Map<Integer, Worker> idMap = new LinkedHashMap<>();
//    private Map<Organization, Map<Integer, Worker>> OrganizationMap = new LinkedHashMap<>();
//    private Set<Organization> organizations = new HashSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    /**
     * @return map of workers
     */
    public Map<Integer, Worker> getKeyMap() {
        return keyMap;
    }

    /**
     * @return Worker by their id
     */
    public Worker byId(int id) {
//        return idMap.get(id);
        for (Worker worker : this) {
            if (worker.getId() == id) return worker;
        }
        return null;
    }

    /**
     * @return Worker by their key
     */
    public Worker byKey(Integer key) {
        return keyMap.get(key);
    }

//    @Deprecated
//    public int maxId() {
//        return Collections.max(idMap.keySet());
//    }


    /**
     * Finds free ID
     * @return Free ID
     */
    public int getFreeId() {
        while (byId(currentId) != null)
            if (++currentId < 0)
                currentId = 1;
        return currentId;
    }


    public boolean isContain(Worker worker) {
        return worker == null || byId(worker.getId()) != null;
    }

    /**
     * Clears keyMap and idMap
     */
    public void clear() {
        keyMap.clear();
//        idMap.clear();
    }

    /**
     * Adds worker to maps
     * @param worker added worker
     */
    public boolean add(Worker worker) {
//        id = max(collection.keySet()) + 1;
        if (isContain(worker)) return false;
        keyMap.put(worker.getKey(), worker);
//        idMap.put(worker.getId(), worker);
        update();
        return true;
//        organizations.add(worker.getOrganization());
//        worker.getOrganization().EmployeeAdded();
    }

    /**
     * Removes worker from maps by its key
     * @param key worker's key
     * @return true if worker was successfully removed
     and false if worker was never found
     */
    public boolean removeByKey(Integer key) {
        var worker = byKey(key);
        if (worker == null) return false;
        keyMap.remove(key);
//        idMap.remove(worker.getId());
        update();
        return true;
    }

//    /**
//     * Removes worker from maps
//     * @param worker worker
//     * @return true if worker was successfully removed
//    and false if worker was never found
//     */
//    public boolean remove(Worker worker) {
//        if (worker == null || !keyMap.containsValue(worker)) return false;
//        keyMap.remove(worker.getKey());
//        idMap.remove(worker.getId());
//        return true;
//    }

    /**
     * Sorts collection
     */
    public void update() {
        Map<Integer, Worker> sortedMap = keyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        keyMap.clear();
        keyMap.putAll(sortedMap);
    }

    /**
     * Downloads collection from file
     * @return true if collection was downloaded successfully
     */
    public boolean init() {
//        idMap.clear();
        keyMap.clear();
        dumpManager.readCsv(keyMap);
        lastInitTime = LocalDateTime.now();
        for (Worker worker : keyMap.values()) {
//            if (byId(e.getId()) != null) {
            if (!worker.validate()) {
                keyMap.clear();
//                idMap.clear();
//                throw new RuntimeException();
                return false;
            }
//            else {
////                idMap.put(e.getId(), e);
//                keyMap.put(e.getKey(), e);
//            }
        }
        update();
        return true;
    }

    /**
     * Saves collection to file
     */
    public void saveMap() {
        dumpManager.writeCsv(keyMap);
        lastSaveTime = LocalDateTime.now();
    }




//    public void showCollectionInfo() {
//
//    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    @Override
    public String toString() {
        if (keyMap.isEmpty()) return "Collection is empty";
        StringBuilder info = new StringBuilder();
        for (var worker : keyMap.values()) {
            info.append(worker.toString()).append("\n");
        }
        return info.toString().trim();
    }

//    public void test() {
//        for (Worker worker : new CollectionManager(null)) {
//
//        }
//    }
    public static void test(CollectionManager collectionManager) {
        for (Worker worker : collectionManager) {
//        for (Worker worker : this) {
            System.out.println(worker);
        }
    }

    @Override
    public Iterator<Worker> iterator() {
        return new WorkerIterator(keyMap);
    }
    private static class WorkerIterator implements Iterator<Worker> {
//        private Iterator<Map.Entry<Integer, Worker>> iterator;
        private Iterator<Worker> iterator;

        public WorkerIterator(Map<Integer, Worker> keyMap) {
            this.iterator = keyMap.values().iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Worker next() {
            return iterator.next();
        }

    }
}

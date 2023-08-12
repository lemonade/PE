import lombok.Getter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HexagonalTileDifference {

  public static void main(String[] args) {
    var tess = new Tessellation();
    List<BigInteger> maxPDList = new ArrayList<>();
    maxPDList.add(BigInteger.ONE);
    tess.addLayer();
    while (maxPDList.size() < 2000) {
      tess.addLayer();
      long pdComputableLayer = tess.getPDComputableLayer();
      BigInteger common = BigInteger.valueOf(3)
          .multiply(BigInteger.valueOf(pdComputableLayer));
      BigInteger start = common
          .multiply(BigInteger.valueOf(pdComputableLayer - 1))
          .add(BigInteger.TWO);
      BigInteger end = common
          .multiply(BigInteger.valueOf(pdComputableLayer + 1))
          .add(BigInteger.ONE);
      Map<BigInteger, CompletableFuture<Integer>> futures = new HashMap<>();
      for (BigInteger val = start; val.compareTo(end) <= 0; val = val.add(BigInteger.ONE)) {
        BigInteger finalVal = val;
        CompletableFuture<Integer> pdFuture =
            CompletableFuture.supplyAsync(() -> tess.getTileMap().get(finalVal).pd());
        futures.put(val, pdFuture);
      }
      CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0])).join();
      List<BigInteger> res = futures.entrySet().stream()
          .filter(f -> f.getValue().getNow(0) == 3)
          .map(Map.Entry::getKey).collect(Collectors.toList());

      if (!res.isEmpty()) {
        maxPDList.addAll(res);
        System.out.println(
            "New value: " + res + ", layer: " + pdComputableLayer + ". Count = " + maxPDList.size());
      }

      tess.removeLayer(pdComputableLayer - 1);
    }
    System.out.println(maxPDList);
    System.out.println(maxPDList.get(1999));
  }

  @Getter
  static class Tessellation {

    private final Map<BigInteger, HexagonalTile> tileMap;
    private long nextLayer;
    private BigInteger nextTileValue;

    public Tessellation() {
      this.tileMap = new ConcurrentHashMap<>();
      this.tileMap.put(BigInteger.ONE, new HexagonalTile(BigInteger.ONE));
      this.nextLayer = 1L;
      this.nextTileValue = BigInteger.TWO;
    }

    public long getPDComputableLayer() {
      return nextLayer - 2;
    }

    public void addLayer() {
      long startValueOfCurrentLayer = 1;
      if (nextLayer > 1) {
        startValueOfCurrentLayer = 3L * (nextLayer - 1) * (nextLayer - 2) + 2;
      }
      HexagonalTile startTileOfCurrentLayer =
          tileMap.get(BigInteger.valueOf(startValueOfCurrentLayer));
      HexagonalTile newTile = new HexagonalTile(nextTileValue);
      nextTileValue = nextTileValue.add(BigInteger.ONE);
      tileMap.put(newTile.getValue(), newTile);
      startTileOfCurrentLayer.setAdj1(newTile);

      for (int i = 0; i < nextLayer; i++) {
        newTile = new HexagonalTile(nextTileValue);
        nextTileValue = nextTileValue.add(BigInteger.ONE);
        tileMap.get(nextTileValue.subtract(BigInteger.TWO)).setAdj3(newTile);
        tileMap.put(newTile.value, newTile);
      }

      for (int i = 0; i < nextLayer; i++) {
        newTile = new HexagonalTile(nextTileValue);
        nextTileValue = nextTileValue.add(BigInteger.ONE);
        tileMap.get(nextTileValue.subtract(BigInteger.TWO)).setAdj4(newTile);
        tileMap.put(newTile.value, newTile);
      }

      for (int i = 0; i < nextLayer; i++) {
        newTile = new HexagonalTile(nextTileValue);
        nextTileValue = nextTileValue.add(BigInteger.ONE);
        tileMap.get(nextTileValue.subtract(BigInteger.TWO)).setAdj5(newTile);
        tileMap.put(newTile.value, newTile);
      }

      for (int i = 0; i < nextLayer; i++) {
        newTile = new HexagonalTile(nextTileValue);
        nextTileValue = nextTileValue.add(BigInteger.ONE);
        tileMap.get(nextTileValue.subtract(BigInteger.TWO)).setAdj6(newTile);
        tileMap.put(newTile.value, newTile);
      }

      for (int i = 0; i < nextLayer; i++) {
        newTile = new HexagonalTile(nextTileValue);
        nextTileValue = nextTileValue.add(BigInteger.ONE);
        tileMap.get(nextTileValue.subtract(BigInteger.TWO)).setAdj1(newTile);
        tileMap.put(newTile.value, newTile);
      }

      for (int i = 0; i < nextLayer - 1; i++) {
        newTile = new HexagonalTile(nextTileValue);
        nextTileValue = nextTileValue.add(BigInteger.ONE);
        tileMap.get(nextTileValue.subtract(BigInteger.TWO)).setAdj2(newTile);
        tileMap.put(newTile.value, newTile);
      }

      nextLayer++;
    }

    public void removeLayer(long layer) {
      if (layer <= 0) {
        tileMap.get(BigInteger.ONE).destroy();
        tileMap.remove(BigInteger.ONE);
        return;
      }

      BigInteger common = BigInteger.valueOf(3)
          .multiply(BigInteger.valueOf(layer));
      BigInteger start = common
          .multiply(BigInteger.valueOf(layer - 1))
          .add(BigInteger.TWO);
      BigInteger end = common
          .multiply(BigInteger.valueOf(layer + 1))
          .add(BigInteger.ONE);
      for (BigInteger val = start; val.compareTo(end) <= 0; val = val.add(BigInteger.ONE)) {
        tileMap.get(val).destroy();
        tileMap.remove(val);
      }
      Runtime.getRuntime().gc();
    }
  }

  @Getter
  static class HexagonalTile {

    private static final Map<BigInteger, Boolean> primes = new ConcurrentHashMap<>();

    BigInteger value;
    HexagonalTile adj1;
    HexagonalTile adj2;
    HexagonalTile adj3;
    HexagonalTile adj4;
    HexagonalTile adj5;
    HexagonalTile adj6;

    public HexagonalTile(BigInteger value) {
      this.value = value;
    }

    public static boolean isPrime(BigInteger number) {
      if (primes.containsKey(number)) return true;
      if (number.compareTo(BigInteger.ONE) <= 0) {
        return false;
      }

      if (number.compareTo(BigInteger.TWO) <= 0) {
        return true;
      }

      if (number.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
        return false;
      }

      BigInteger sqrt = number.sqrt();
      for (BigInteger i = BigInteger.valueOf(3); i.compareTo(sqrt) <= 0;
           i = i.add(BigInteger.TWO)) {
        if (number.mod(i).equals(BigInteger.ZERO)) {
          return false;
        }
      }

      primes.put(number, true);
      return true;
    }

    public void destroy() {
      if (Objects.nonNull(adj1)) {
        adj1.adj4 = null;
      }
      if (Objects.nonNull(adj2)) {
        adj2.adj5 = null;
      }
      if (Objects.nonNull(adj3)) {
        adj3.adj6 = null;
      }
      if (Objects.nonNull(adj4)) {
        adj4.adj1 = null;
      }
      if (Objects.nonNull(adj5)) {
        adj5.adj2 = null;
      }
      if (Objects.nonNull(adj6)) {
        adj6.adj3 = null;
      }
    }

    public void setAdj1(HexagonalTile tile) {
      this.adj1 = tile;
      if (Objects.isNull(tile.adj4)) {
        tile.setAdj4(this);
      }
      if (Objects.nonNull(adj2) && Objects.isNull(adj2.adj6)) {
        adj2.setAdj6(tile);
      }
      if (Objects.nonNull(adj6) && Objects.isNull(adj6.adj2)) {
        adj6.setAdj2(tile);
      }
    }

    public void setAdj2(HexagonalTile tile) {
      this.adj2 = tile;
      if (Objects.isNull(tile.adj5)) {
        tile.setAdj5(this);
      }
      if (Objects.nonNull(adj1) && Objects.isNull(adj1.adj3)) {
        adj1.setAdj3(tile);
      }
      if (Objects.nonNull(adj3) && Objects.isNull(adj3.adj1)) {
        adj3.setAdj1(tile);
      }
    }

    public void setAdj3(HexagonalTile tile) {
      this.adj3 = tile;
      if (Objects.isNull(tile.adj6)) {
        tile.setAdj6(this);
      }
      if (Objects.nonNull(adj2) && Objects.isNull(adj2.adj4)) {
        adj2.setAdj4(tile);
      }
      if (Objects.nonNull(adj4) && Objects.isNull(adj4.adj2)) {
        adj4.setAdj2(tile);
      }
    }

    public void setAdj4(HexagonalTile tile) {
      this.adj4 = tile;
      if (Objects.isNull(tile.adj1)) {
        tile.setAdj1(this);
      }
      if (Objects.nonNull(adj3) && Objects.isNull(adj3.adj5)) {
        adj3.setAdj5(tile);
      }
      if (Objects.nonNull(adj5) && Objects.isNull(adj5.adj3)) {
        adj5.setAdj3(tile);
      }
    }

    public void setAdj5(HexagonalTile tile) {
      this.adj5 = tile;
      if (Objects.isNull(tile.adj2)) {
        tile.setAdj2(this);
      }
      if (Objects.nonNull(adj6) && Objects.isNull(adj6.adj4)) {
        adj6.setAdj4(tile);
      }
      if (Objects.nonNull(adj4) && Objects.isNull(adj4.adj6)) {
        adj4.setAdj6(tile);
      }
    }

    public void setAdj6(HexagonalTile tile) {
      this.adj6 = tile;
      if (Objects.isNull(tile.adj3)) {
        tile.setAdj3(this);
      }
      if (Objects.nonNull(adj1) && Objects.isNull(adj1.adj5)) {
        adj1.setAdj5(tile);
      }
      if (Objects.nonNull(adj5) && Objects.isNull(adj5.adj1)) {
        adj5.setAdj1(tile);
      }
    }

    @Override
    public String toString() {
      return value + " [" + (Objects.nonNull(adj1) ? adj1.value : null)
          + ", " + (Objects.nonNull(adj2) ? adj2.value : null)
          + ", " + (Objects.nonNull(adj3) ? adj3.value : null)
          + ", " + (Objects.nonNull(adj4) ? adj4.value : null)
          + ", " + (Objects.nonNull(adj5) ? adj5.value : null)
          + ", " + (Objects.nonNull(adj6) ? adj6.value : null) + "]";
    }

    public int pd() {
      return (int) Stream.of(adj1, adj2, adj3, adj4, adj5, adj6)
          .map(adj -> value.subtract(adj.value).abs())
          .filter(HexagonalTile::isPrime).count();
    }
  }
}

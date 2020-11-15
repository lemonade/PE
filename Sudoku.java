package pe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Sudoku {
	private static final List<Integer> FULL_LIST = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("p096_sudoku.txt")));
		String line = null;
		int sum = 0;
		while ((line = br.readLine()) != null) {
			if (line.contains("Grid")) {
				System.out.println(line);
			}
			int[][] puzzle = new int[9][9];
			for (int i = 0; i < 9; i++) {
				String row = br.readLine();
				System.out.println(row);
				for (int j = 0; j < 9; j++) {
					puzzle[i][j] = row.charAt(j) - '0';
				}
			}
			sum += solve(puzzle);
		}
		br.close();
		System.out.println("Sum = " + sum);
	}

	public static class FillOption {
		int i;
		int j;
		List<Integer> values;

		public FillOption(int i, int j, List<Integer> values) {
			this.i = i;
			this.j = j;
			this.values = values;
		}

		public void removeOption() {
			this.values.remove(0);
		}
	}

	private static int solve(int[][] puzzle) {
		return solve(puzzle, new Stack<>());
	}

	private static int solve(int[][] puzzle, Stack<FillOption> fills) {
		if (isSolved(puzzle)) {
			System.out.println("Answer:");
			print(puzzle);
			return Integer.valueOf(puzzle[0][0] + "" + puzzle[0][1] + "" + puzzle[0][2]);
		}

		FillOption fillOption = getOptions(puzzle).get(0);
		if (fillOption.values.size() == 0) {
			fills.peek().removeOption();
			while (fills.peek().values.isEmpty()) {
				puzzle[fills.peek().i][fills.peek().j] = 0;
				fills.pop();
				if (fills.empty()) {
					throw new RuntimeException("Cannot solve ...");
				}
				fills.peek().removeOption();
			}
			puzzle[fills.peek().i][fills.peek().j] = fills.peek().values.get(0);
			return solve(puzzle, fills);
		} else {
			fills.push(fillOption);
			puzzle[fillOption.i][fillOption.j] = fillOption.values.get(0);
			return solve(puzzle, fills);
		}
	}

	private static List<FillOption> getOptions(int[][] puzzle) {
		List<FillOption> output = new ArrayList<>();
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				if (puzzle[i][j] == 0) {
					output.add(getOptions(puzzle, i, j));
				}
			}
		}
		output.sort((o1, o2) -> o1.values.size() - o2.values.size());
		return output;
	}

	private static FillOption getOptions(int[][] puzzle, int r, int c) {
		List<Integer> row = new ArrayList<>();
		for (int j = 0; j < puzzle[0].length; j++) {
			if (puzzle[r][j] != 0) {
				row.add(puzzle[r][j]);
			}
		}

		List<Integer> col = new ArrayList<>();
		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i][c] != 0) {
				col.add(puzzle[i][c]);
			}
		}

		List<Integer> local = new ArrayList<>();
		int i0 = r / 3;
		int j0 = c / 3;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (puzzle[i0 * 3 + i][j0 * 3 + j] != 0) {
					local.add(puzzle[i0 * 3 + i][j0 * 3 + j]);
				}
			}
		}

		List<Integer> values = FULL_LIST.stream().filter(x -> !row.contains(x)).filter(x -> !col.contains(x))
				.filter(x -> !local.contains(x)).collect(Collectors.toList());
		return new FillOption(r, c, values);
	}

	private static boolean isSolved(int[][] puzzle) {
		for (int i = 0; i < puzzle.length; i++) {
			List<Integer> tmp = new ArrayList<>();
			for (int j = 0; j < puzzle[i].length; j++) {
				tmp.add(puzzle[i][j]);
			}
			if (!isFull(tmp)) {
				return false;
			}
		}

		for (int i = 0; i < puzzle[0].length; i++) {
			List<Integer> tmp = new ArrayList<>();
			for (int j = 0; j < puzzle.length; j++) {
				tmp.add(puzzle[i][j]);
			}
			if (!isFull(tmp)) {
				return false;
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				List<Integer> tmp = List.of(puzzle[3 * i][3 * j], puzzle[3 * i][3 * j + 1], puzzle[3 * i][3 * j + 2],
						puzzle[3 * i + 1][3 * j], puzzle[3 * i + 1][3 * j + 1], puzzle[3 * i + 1][3 * j + 2],
						puzzle[3 * i + 2][3 * j], puzzle[3 * i + 2][3 * j + 1], puzzle[3 * i + 2][3 * j + 2]);
				if (!isFull(tmp)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isFull(List<Integer> list) {
		Set<Integer> set = new HashSet<>();
		set.addAll(list);
		if (set.size() != 9) {
			return false;
		}

		if (set.stream().reduce((i1, i2) -> Math.min(i1, i2)).get() != 1) {
			return false;
		}

		if (set.stream().reduce((i1, i2) -> Math.max(i1, i2)).get() != 9) {
			return false;
		}

		return true;
	}

	private static void print(int[][] puzzle) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[0].length; j++) {
				sb.append(puzzle[i][j]);
			}
			sb.append('\n');
		}
		System.out.println(sb.toString());
	}
}

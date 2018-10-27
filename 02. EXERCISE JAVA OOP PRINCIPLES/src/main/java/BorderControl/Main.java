package BorderControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        List<ControlSystem> citizens = new ArrayList<ControlSystem>();

        String input;
        while (!"End".equals(input = reader.readLine())) {
            String[] tokens = input.split(" ");

            if (tokens.length == 2) {
                String model = tokens[0];
                String id = tokens[1];

                citizens.add(new Robot(model, id));
            } else {
                String name = tokens[0];
                int age = Integer.parseInt(tokens[1]);
                String id = tokens[2];

                citizens.add(new Citizens(name, age, id));
            }
        }

        String fakeIds = reader.readLine();
        for (ControlSystem citizen : citizens) {
            if(citizen.checkId(fakeIds)) {
                System.out.println(citizen.getId());
                citizens.remove(citizen);
            }
        }
    }
}

package inside.commands.params;

import arc.struct.Seq;

class IntVariadicParameter extends IntParameter implements VariadicParameter<Integer> {

    IntVariadicParameter(String name, boolean optional) {
        super(name, optional, true);
    }

    @Override
    public Seq<Integer> parseMultiple(String value) {
        String[] parts = value.split(" ");
        Seq<Integer> values = new Seq<>(true, parts.length, Integer.class);
        for (String part : parts) {
            values.add(parse(part));
        }
        return values;
    }
}

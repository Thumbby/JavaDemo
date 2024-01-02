
public class ServiceImpl implements Service{

    @Override
    public String test(String clientName) {
        return "Test " + clientName + "...";
    }
}

package mtopsdk.mtop.global.init;

public interface IMtopInitTask {
    void executeCoreTask(ffd ffd);

    void executeExtraTask(ffd ffd);
}

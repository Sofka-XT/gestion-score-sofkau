package co.com.sofka.wsscore.domain.program.command;

import co.com.sofka.wsscore.domain.generic.Command;

public class AssignScoreCommand extends Command {
    private  String programId;
    private  String path;

    public String getProgramId() {
        return programId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }
}

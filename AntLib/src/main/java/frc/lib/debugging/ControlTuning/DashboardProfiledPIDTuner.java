// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.debugging.ControlTuning;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

/** Add your docs here. */
public class DashboardProfiledPIDTuner{
    private DashboardTuner pTuner = new DashboardTuner("P");
    private DashboardTuner iTuner = new DashboardTuner("I");
    private DashboardTuner dTuner = new DashboardTuner("D");
    private DashboardTuner vTuner = new DashboardTuner("V");
    private DashboardTuner aTuner = new DashboardTuner("A");
    ShuffleboardLayout layout;

    public DashboardProfiledPIDTuner(String name){
        layout = Shuffleboard.getTab("Tuning").getLayout(name, BuiltInLayouts.kList);
        System.out.println("+++++++++++" + SendableRegistry.getName(pTuner).toString());
        layout.add(pTuner);
        layout.add(iTuner);
        layout.add(dTuner);
        layout.add(vTuner);
        layout.add(aTuner);
    }

    public ProfiledPIDConfig getConfig(){
        return new ProfiledPIDConfig(
            pTuner.getValue(),
            iTuner.getValue(),
            dTuner.getValue(),
            vTuner.getValue(),
            aTuner.getValue()
            );
    }
}

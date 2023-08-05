package kg.kuban.airport.entity;

import kg.kuban.airport.enums.InspectionStatus;
import kg.kuban.airport.enums.WearDegree;

import javax.persistence.*;
import java.time.LocalDate;

/*

Технический осмотр пассажирского самолета - это процедура, которая выполняется перед каждым полетом,
чтобы убедиться в том, что самолет находится в рабочем состоянии и безопасен для полета. Она включает в себя следующие этапы:

1. Визуальный осмотр.
Техник осматривает самолет на предмет повреждений, различных деформаций, протекания топлива,
повреждений резины шасси и других проблем, которые могут повлиять на безопасность полета.

2. Проверка технического состояния двигателей. Двигатели проверяются на наличие люфта и корректности работы.

3. Проверка электронных систем. Компьютерные системы и приборы внутри кабины должны быть проверены,
чтобы убедиться, что все они работают должным образом.

4. Заправка топлива. Заправщик заправляет самолет топливом, заодно проверяя наличие топливных утечек.

5. Взлетно-посадочное снаряжение. Техник осматривает шасси, тормозные системы и тормозные котлы, чтобы убедиться в их исправности.

6. Радиосвязь. Коммуникационное оборудование проверяется на наличие перебоев в работе.

7. Исправные документы. Техник убеждается в том, что все документы, необходимые для полета, находятся на борту.

8. Тестовый полет. При необходимости выполняется тестовый полет для проверки работоспособности самолета.

Таким образом, технический осмотр пассажирского самолета включает в себя много этапов, чтобы обеспечить безопасность на борту и гарантировать, что самолет находится в полной работоспособности перед вылетом.
 */

@Entity
@Table(name = "tech_inspections")
public class TechInspection {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chief_engineer_id", referencedColumnName = "id")
    private AppUser chiefEngineer;              // Инженер, который провел технический осмотр
    @Column(name = "date_inspection")
    private LocalDate dateInspection;            // Дата технического обслуживания

    @Enumerated(EnumType.STRING)
    @Column(name = "degree")
    private WearDegree degree;            // Степень износа

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InspectionStatus status;            // Статус
    @Column(name = "result")
    private String result;            // Результат

    /*
    private Airplane aircraft;              // Самолет, по которому проводят техническое обслуживание
    private Employee engineer;              // Инженер, который провел технический осмотр
    private LocalDate dateInspection;            // Дата технического обслуживания

    private LocalDate dateNext;             // Дата следующего технического обслуживания самолета, которое нужно выполнить для поддержания его работоспособности.
    private String number;                  // Номер технического обслуживания
    private List<TechStage> listTechState;  // Состояния технического обслуживания по типам
    private Boolean isGeneralOk;            // Общий технический Статус
    private Boolean isRefueled;             // Признак заправлен топливом самолет или нет
*/

    //    public void AddTechInspection(Airplane airplane) { // добавить Технический Осмотр
    //        listInspections.add(techInspection);
    //    }


    public TechInspection() {
    }

    public Long getId() {
        return id;
    }

    public TechInspection setId(Long id) {
        this.id = id;
        return this;
    }

    public AppUser getChiefEngineer() {
        return chiefEngineer;
    }

    public TechInspection setChiefEngineer(AppUser chiefEngineer) {
        this.chiefEngineer = chiefEngineer;
        return this;
    }

    public LocalDate getDateInspection() {
        return dateInspection;
    }

    public TechInspection setDateInspection(LocalDate dateInspection) {
        this.dateInspection = dateInspection;
        return this;
    }

    public WearDegree getDegree() {
        return degree;
    }

    public TechInspection setDegree(WearDegree degree) {
        this.degree = degree;
        return this;
    }

    public InspectionStatus getStatus() {
        return status;
    }

    public TechInspection setStatus(InspectionStatus status) {
        this.status = status;
        return this;
    }

    public String getResult() {
        return result;
    }

    public TechInspection setResult(String result) {
        this.result = result;
        return this;
    }
}

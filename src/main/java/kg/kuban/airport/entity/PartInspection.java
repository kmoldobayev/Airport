package kg.kuban.airport.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kg.kuban.airport.enums.AirplanePartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**

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

@Schema(name = "Сущность История техосмотра самолета", description = "Описывает сущность Истории техосмотра самолета")
@Entity
@Table(name = "part_inspections")
public class PartInspection {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser appUser;              // Инженер, который провел технический осмотр
    @Column(name = "date_register")
    private LocalDateTime dateRegister;            // Дата технического обслуживания

    @ManyToOne
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    private AirplanePart part;              //

    @ManyToOne
    @JoinColumn(name = "airplane_id", referencedColumnName = "id")
    private Airplane airplane;              //


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AirplanePartStatus status;            // Статус тех осмотра
    @Column(name = "inspection_code")
    private Long inspectionCode;            //



    public PartInspection() {
    }

    public Long getId() {
        return id;
    }

    public PartInspection setId(Long id) {
        this.id = id;
        return this;
    }


    public AirplanePartStatus getStatus() {
        return status;
    }

    public PartInspection setStatus(AirplanePartStatus status) {
        this.status = status;
        return this;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public PartInspection setAppUser(AppUser appUser) {
        this.appUser = appUser;
        return this;
    }

    public LocalDateTime getDateRegister() {
        return dateRegister;
    }

    public PartInspection setDateRegister(LocalDateTime dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public AirplanePart getPart() {
        return part;
    }

    public PartInspection setPart(AirplanePart part) {
        this.part = part;
        return this;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public PartInspection setAirplane(Airplane airplane) {
        this.airplane = airplane;
        return this;
    }

    public Long getInspectionCode() {
        return inspectionCode;
    }

    public PartInspection setInspectionCode(Long inspectionCode) {
        this.inspectionCode = inspectionCode;
        return this;
    }
}

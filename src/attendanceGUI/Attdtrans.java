/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceGUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "ATTDTRANS", catalog = "", schema = "ATTENDANCE")
@NamedQueries({
    @NamedQuery(name = "Attdtrans.findAll", query = "SELECT a FROM Attdtrans a"),
    @NamedQuery(name = "Attdtrans.findByTime", query = "SELECT a FROM Attdtrans a WHERE a.time = :time"),
    @NamedQuery(name = "Attdtrans.findByBarcode", query = "SELECT a FROM Attdtrans a WHERE a.barcode = :barcode")})
public class Attdtrans implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Basic(optional = false)
    @Column(name = "BARCODE")
    private String barcode;
    @Lob
    @Column(name = "EXCUSE")
    private String excuse;

    public Attdtrans() {
    }

    public Attdtrans(Date time) {
        this.time = time;
    }

    public Attdtrans(Date time, String barcode) {
        this.time = time;
        this.barcode = barcode;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        Date oldTime = this.time;
        this.time = time;
        changeSupport.firePropertyChange("time", oldTime, time);
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        String oldBarcode = this.barcode;
        this.barcode = barcode;
        changeSupport.firePropertyChange("barcode", oldBarcode, barcode);
    }

    public String getExcuse() {
        return excuse;
    }

    public void setExcuse(String excuse) {
        String oldExcuse = this.excuse;
        this.excuse = excuse;
        changeSupport.firePropertyChange("excuse", oldExcuse, excuse);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (time != null ? time.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attdtrans)) {
            return false;
        }
        Attdtrans other = (Attdtrans) object;
        if ((this.time == null && other.time != null) || (this.time != null && !this.time.equals(other.time))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "attendanceGUI.Attdtrans[ time=" + time + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}

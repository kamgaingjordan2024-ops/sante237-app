import { useState, useEffect } from "react";

const API = "http://localhost:8081";

export default function Notifications({ onNavigate }) {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(false);

  const token = localStorage.getItem("token");
  const user = JSON.parse(localStorage.getItem("user") || "{}");
  const userId = user?.id;

  const headers = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };

  const fetchNotifications = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API}/api/notifications/utilisateur/${userId}`, { headers });
      if (res.ok) setNotifications(await res.json());
    } catch { console.error("Erreur chargement notifications"); }
    finally { setLoading(false); }
  };

  const marquerLue = async (id) => {
    try {
      await fetch(`${API}/api/notifications/${id}`, {
        method: "PUT",
        headers,
        body: JSON.stringify({ lue: true, message: "", utilisateurId: userId }),
      });
      fetchNotifications();
    } catch { console.error("Erreur mise à jour notification"); }
  };

  useEffect(() => { fetchNotifications(); }, []);

  const nonLues = notifications.filter(n => !n.lue).length;

  return (
    <div style={{ minHeight: "100vh", background: "linear-gradient(135deg, #f0f7ff, #dbeafe)", padding: 24 }}>
      <div style={{ maxWidth: 600, margin: "0 auto" }}>

        <button onClick={() => onNavigate("select-hopital")} style={{
          background: "none", border: "none", cursor: "pointer",
          color: "#1a5fd4", fontSize: 14, fontWeight: 600, marginBottom: 24,
        }}>
          ← Retour
        </button>

        <h1 style={{ fontSize: 24, fontWeight: 700, color: "#0f172a", marginBottom: 8 }}>
          🔔 Notifications
          {nonLues > 0 && (
            <span style={{
              marginLeft: 10, background: "#dc2626", color: "white",
              borderRadius: 20, padding: "2px 10px", fontSize: 13
            }}>
              {nonLues}
            </span>
          )}
        </h1>

        {loading && <p style={{ color: "#64748b" }}>Chargement...</p>}

        {!loading && notifications.length === 0 && (
          <div style={{
            background: "white", borderRadius: 20, padding: 32,
            textAlign: "center", color: "#94a3b8"
          }}>
            Aucune notification pour le moment.
          </div>
        )}

        {notifications.map((n) => (
          <div key={n.id_notification} onClick={() => !n.lue && marquerLue(n.id_notification)} style={{
            background: n.lue ? "white" : "#eff6ff",
            borderRadius: 16, padding: 20, marginBottom: 12,
            boxShadow: "0 2px 8px rgba(26,95,212,0.08)",
            cursor: n.lue ? "default" : "pointer",
            borderLeft: n.lue ? "4px solid #e2e8f0" : "4px solid #1a5fd4",
          }}>
            <p style={{ margin: 0, fontWeight: n.lue ? 400 : 600, color: "#0f172a" }}>
              {n.message}
            </p>
            <div style={{ display: "flex", justifyContent: "space-between", marginTop: 8 }}>
              <span style={{ fontSize: 12, color: "#94a3b8" }}>
                {n.dateEnvoi ? new Date(n.dateEnvoi).toLocaleDateString("fr-FR", {
                  day: "numeric", month: "long", hour: "2-digit", minute: "2-digit"
                }) : ""}
              </span>
              {!n.lue && (
                <span style={{ fontSize: 11, color: "#1a5fd4", fontWeight: 600 }}>
                  Cliquez pour marquer comme lue
                </span>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
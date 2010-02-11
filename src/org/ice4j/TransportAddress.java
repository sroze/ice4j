/*
 * ice4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 * Maintained by the SIP Communicator community (http://sip-communicator.org).
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package org.ice4j;

import java.net.*;

import org.ice4j.ice.*;

/**
 * The Address class is used to define destinations to outgoing Stun Packets.
 *
 * @author Emil Ivov
 * @author Lubomir Marinov
 */
public class TransportAddress
    extends InetSocketAddress
{
    /**
     * our serial version UID;
     */
    private static final long serialVersionUID = 5076001401234631237L;

    /**
     * The variable that we are using to store the transport that this address
     * is pertaining to.
     */
    private final Transport transport;

    /**
     * Creates an address instance address from an IP address and a port number.
     * <p>
     * A valid port value is between 0 and 65535.
     * A port number of <tt>zero</tt> will let the system pick up an
     * ephemeral port in a <tt>bind</tt> operation.
     * <P>
     * A <tt>null</tt> address will assign the <i>wildcard</i> address.
     * <p>
     * @param   hostname    The IP address
     * @param   port        The port number
     * @param   transport   The transport that this address would be bound to.
     * @throws IllegalArgumentException if the port parameter is outside the
     * specified range of valid port values.
     */
    public TransportAddress(String hostname, int port, Transport transport)
    {
        super(hostname, port);
        this.transport = transport;
    }

    /**
     * Creates an address instance address from a byte array containing an IP
     * address and a port number.
     * <p>
     * A valid port value is between 0 and 65535.
     * A port number of <tt>zero</tt> will let the system pick up an
     * ephemeral port in a <tt>bind</tt> operation.
     * <P>
     * A <tt>null</tt> address will assign the <i>wildcard</i> address.
     * <p>
     * @param    ipAddress The IP address
     * @param    port      The port number
     * @param    transport The <tt>Transport</tt> to use with this address.
     *
     * @throws UnknownHostException UnknownHostException  if IP address is of
     * illegal length
     */
    public TransportAddress(byte[] ipAddress, int port, Transport transport)
        throws UnknownHostException
    {
        super(InetAddress.getByAddress(ipAddress), port);

        this.transport = transport;
    }

    /**
     * Creates an address instance from an <tt>InetSocketAddress</tt>.
     *
     * @param    address   the address and port.
     * @param    transport the transport to use with this address.
     *
     * @throws IllegalArgumentException if the port parameter is outside the
     * range of valid port values, or if the host name parameter is
     * <tt>null</tt>.
     */
    public TransportAddress(InetSocketAddress address, Transport transport)
    {
        super(address.getAddress(), address.getPort());
        this.transport = transport;
    }

    /**
     * Creates an address instance from a host name and a port number.
     * <p>
     * An attempt will be made to resolve the host name into an InetAddress.
     * If that attempt fails, the address will be flagged as <I>unresolved</I>.
     * <p>
     * A valid port value is between 0 and 65535. A port number of zero will
     * let the system pick up an ephemeral port in a <tt>bind</tt> operation.
     * <p>
     * @param    address   the address itself
     * @param    port      the port number
     * @param    transport the transport to use with this address.
     *
     * @throws IllegalArgumentException if the port parameter is outside the
     * range of valid port values, or if the host name parameter is
     * <tt>null</tt>.
     */
    public TransportAddress(InetAddress address, int port, Transport transport)
    {
        super(address, port);
        this.transport = transport;
    }

    /**
     * Returns the raw IP address of this Address object. The result is in
     * network byte order: the highest order byte of the address is in
     * getAddress()[0].
     *
     * @return the raw IP address of this object.
     */
    public byte[] getAddressBytes()
    {
        return getAddress().getAddress();
    }

    /**
     * Constructs a string representation of this InetSocketAddress. This String
     * is constructed by calling toString() on the InetAddress and concatenating
     * the port number (with a colon). If the address is unresolved then the
     * part before the colon will only contain the host name.
     *
     * @return a string representation of this object.
     */
    public String toString()
    {
        return super.toString() + "/"+getTransport();
    }

    /**
     * Returns the host address.
     *
     * @return a String part of the address
     */
    public String getHostAddress()
    {
        InetAddress addr = getAddress();

        String addressStr = addr.getHostAddress();

        if(addr instanceof Inet6Address)
            addressStr = NetworkUtils.stripScopeID(addressStr);

        return addressStr;
    }

    /**
     * The transport that this transport address is suggesting.
     *
     * @return one of the transport strings (UDP/TCP/...) defined as contants
     * in this class.
     */
    public Transport getTransport()
    {
        return transport;
    }

    /**
     * Determines whether this <tt>TransportAddress</tt> is value equal to a
     * specific <tt>TransportAddress</tt>.
     *
     * @param transportAddress the <tt>TransportAddress</tt> to test for value
     * equality with this <tt>TransportAddress</tt>
     * @return <tt>true</tt> if this <tt>TransportAddress</tt> is value equal to
     * the specified <tt>transportAddress</tt>; otherwise, <tt>false</tt>
     * @see #equalsTransportAddress(Object)
     */
    public boolean equals(TransportAddress transportAddress)
    {
        return equalsTransportAddress(transportAddress);
    }

    /**
     * Compares this object against the specified object. The result is
     * <tt>true</tt> if and only if the argument is not <tt>null</tt> and it
     * represents the same address.
     * <p>
     * Two instances of <tt>TransportAddress</tt> represent the same
     * address if both the InetAddresses (or hostnames if it is unresolved),
     * port numbers, and <tt>Transport</tt>s are equal.
     *
     * If both addresses are unresolved, then the hostname, the port & and
     * the <tt>Transport</tt> are compared.
     *
     * @param   obj   the object to compare against.
     * @return  <tt>true</tt> if the objects are the same and
     * <tt>false</tt> otherwise.
     * @see java.net.InetAddress#equals(java.lang.Object)
     */
    public boolean equalsTransportAddress(Object obj)
    {
        return super.equals(obj)
            &&(  ((TransportAddress)obj).getTransport() == getTransport() );
    }
}
